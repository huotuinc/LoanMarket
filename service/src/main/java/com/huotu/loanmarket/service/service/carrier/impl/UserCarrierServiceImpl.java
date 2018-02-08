package com.huotu.loanmarket.service.service.carrier.impl;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.common.utils.GZipUtils;
import com.huotu.loanmarket.service.config.LoanMarkConfigProvider;
import com.huotu.loanmarket.service.entity.carrier.ActiveSilenceStats;
import com.huotu.loanmarket.service.entity.carrier.ConsumeBill;
import com.huotu.loanmarket.service.entity.carrier.FinanceContactDetail;
import com.huotu.loanmarket.service.entity.carrier.FinanceContactStats;
import com.huotu.loanmarket.service.entity.carrier.RiskContactDetail;
import com.huotu.loanmarket.service.entity.carrier.RiskContactStats;
import com.huotu.loanmarket.service.entity.carrier.UserCarrier;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import com.huotu.loanmarket.service.handler.JsonResponseHandler;
import com.huotu.loanmarket.service.model.CarrierConfig;
import com.huotu.loanmarket.service.model.carrier.UserCarrierVo;
import com.huotu.loanmarket.service.repository.carrier.ActiveSilenceStatsRepository;
import com.huotu.loanmarket.service.repository.carrier.ConsumeBillRepository;
import com.huotu.loanmarket.service.repository.carrier.FinanceContactDetailRepository;
import com.huotu.loanmarket.service.repository.carrier.FinanceContactStatsRepository;
import com.huotu.loanmarket.service.repository.carrier.RiskContactDetailRepository;
import com.huotu.loanmarket.service.repository.carrier.RiskContactStatsRepository;
import com.huotu.loanmarket.service.repository.carrier.UserCarrierRepository;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.service.carrier.UserCarrierService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luyuanyuan on 2018/1/30.
 */
@Service
public class UserCarrierServiceImpl implements UserCarrierService {
    private static final Log log = LogFactory.getLog(UserCarrierServiceImpl.class);

    @Autowired
    private UserCarrierRepository userCarrierRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RiskContactStatsRepository riskContactStatsRepository;
    @Autowired
    private ConsumeBillRepository consumeBillRepository;
    @Autowired
    private LoanMarkConfigProvider loanMarkConfigProvider;
    @Autowired
    private RiskContactDetailRepository riskContactDetailRepository;
    @Autowired
    private FinanceContactStatsRepository financeContactStatsRepository;
    @Autowired
    private FinanceContactDetailRepository financeContactDetailRepository;
    @Autowired
    private ActiveSilenceStatsRepository activeSilenceStatsRepository;

    private HttpClientBuilder httpClientBuilder;
    //    private Gson gson = new GsonBuilder().serializeNulls().create();


    @PostConstruct
    public void init() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(30000).build();
        httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultRequestConfig(requestConfig).setUserAgent(Constant.DefaultAgent);
    }


    @Override
    @Transactional
    public ApiResult queryResult(String taskId, String orderId, Integer merchantId) throws IOException {
        //获取运营商系统参数
        CarrierConfig carrierConfig = loanMarkConfigProvider.getCarrierConfig(merchantId);
        String code = carrierConfig.getPartnerCode();
        String key = carrierConfig.getPartnerKey();
        String url = "https://api.shujumohe.com/octopus/task.unify.query/v3?partner_code=" + code + "&partner_key=" + key;
        //1.先通过数据魔盒获取运营商原始数据
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(
                    EntityBuilder
                            .create()
                            .setContentType(ContentType.APPLICATION_FORM_URLENCODED).setContentEncoding("UTF-8")
                            .setParameters(new BasicNameValuePair("task_id", taskId))
                            .build()
            );

            JsonObject jsonObject = httpClient.execute(httpPost, new JsonResponseHandler());
            String operatorMessage = jsonObject.get("message").getAsString();
            int operatorCode = jsonObject.get("code").getAsInt();
            boolean flag = false;
            String message = operatorMessage;
            String resultStr = "";
            Order order = orderRepository.findOne(orderId);
            order.setTaskId(taskId);
            if (operatorCode == 0) {
                //身份证号码。真实姓名
                //处理运营商数据，保存原始运营商数据一份
                JsonObject data = jsonObject.getAsJsonObject("data");
                UserCarrier userCarrier = userCarrierRepository.findByOrderIdAndMerchantId(orderId, merchantId);
                if (userCarrier == null) {
                    userCarrier = new UserCarrier();
                    userCarrier.setFirstUpdatetime(LocalDateTime.now());
                }
                userCarrier.setOrderId(orderId);
                //不能直接data.getAsString 会报错
//                resultStr = gson.toJson(data);
//                userCarrier.setInfo(resultStr);
                userCarrier.setLastUpdatetime(LocalDateTime.now());
                userCarrier.setMerchantId(merchantId);
                JsonObject magicJsonObject = null;
                String magicMessage = null;
                int magicCode = -1;
                magicJsonObject = magicReport(taskId, merchantId);
                magicMessage = magicJsonObject.get("msg").getAsString();
                magicCode = magicJsonObject.get("code").getAsInt();
                log.info("magicJsonObject msg:" + magicMessage);
                log.info("magicJsonObject code:" + magicCode);
                log.info("magicCode:" + magicCode);
                if (magicCode == 0) {
                    assembleReportData(orderId, magicJsonObject, userCarrier);
                    flag = true;
                    message = "认证成功";
                } else {
                    //认证失败
                    message = magicMessage;
                    log.info("数据魔盒返回数据报告失败");
                }
            }
            int resultCode;
            if (flag) {
                order.setAuthStatus(UserAuthorizedStatusEnums.AUTH_SUCCESS);
                order.getUser().setAuthStatus(UserAuthorizedStatusEnums.AUTH_SUCCESS);
                resultCode = AppCode.SUCCESS.getCode();
            } else {
                order.setAuthStatus(UserAuthorizedStatusEnums.AUTH_ERROR);
                resultCode = AppCode.ERROR.getCode();
            }
            order.setAuthTime(LocalDateTime.now());
            orderRepository.saveAndFlush(order);
            return ApiResult.resultWith(resultCode, message);
        }
    }

    private void assembleReportData(String orderId, JsonObject jsonObject1, UserCarrier userCarrier) {
        JsonObject data1 = jsonObject1.getAsJsonObject("data");
        //保存风险联系人数据
        saveRiskContactStats(orderId, data1);
        log.info("保存风险联系人数据");
        //保存风险联系人详情
        saveRiskContactDetail(orderId, data1);
        log.info("保存风险联系人详情数据");
        //运营商每月账单
        saveConsumeBill(orderId, data1);
        log.info("运营商每月账单");
        //保存金融联系人
        saveFinanceContactStats(orderId, data1);
        log.info("保存金融联系人");
        //保存金融联系人明细
        saveFinanceContactDetail(orderId, data1);
        log.info("保存金融联系人明细");
        //保存静默活跃统计
        saveActiveSilenceStats(orderId, data1);
        log.info("保存静默活跃统计");

        //入网时间
        JsonObject mobileInfo = data1.getAsJsonObject("mobile_info");
        String mobileNetTime = !mobileInfo.get("mobile_net_time").isJsonNull() ?
                mobileInfo.get("mobile_net_time").getAsString() : null;

        //手机号码
        String mobile = !mobileInfo.get("user_mobile").isJsonNull() ?
                mobileInfo.get("user_mobile").getAsString() : null;
        //运营商
        String mobileCarrier = !mobileInfo.get("mobile_carrier").isJsonNull() ?
                mobileInfo.get("mobile_carrier").getAsString() : null;

        //手机归属地
        String mobileAddr = !mobileInfo.get("mobile_net_addr").isJsonNull() ?
                mobileInfo.get("mobile_net_addr").getAsString() : null;
        //运营商状态
        String accountStatus = !mobileInfo.get("account_status").isJsonNull() ?
                mobileInfo.get("account_status").getAsString() : null;
        //用户姓名
        String realName = !mobileInfo.get("real_name").isJsonNull() ?
                mobileInfo.get("real_name").getAsString() : null;
        //身份证号码
        String identityCode = !mobileInfo.get("identity_code").isJsonNull() ?
                mobileInfo.get("identity_code").getAsString() : null;

        //账户余额
        BigDecimal accountBalance = !mobileInfo.get("account_balance").isJsonNull() ?
                mobileInfo.get("account_balance").getAsBigDecimal()
                        .divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP) : null;
        //近6个月互通号码数量
        JsonObject behaviorAnalysis = data1.getAsJsonObject("behavior_analysis");
        String mutualNumber = !behaviorAnalysis.get("mutual_number_analysis_6month").isJsonNull() ?
                behaviorAnalysis.get("mutual_number_analysis_6month").getAsString() : null;
        // 前10联系人黑名单人数占比
        JsonObject blacklistAnalysis = data1.getAsJsonObject("contact_blacklist_analysis");
        Double blackRadio = !blacklistAnalysis.get("black_top10_contact_total_count_ratio").isJsonNull() ?
                blacklistAnalysis.get("black_top10_contact_total_count_ratio").getAsDouble() : null;
        // 前10联系人信贷逾期名单人数占比
        Double blackCreditRadio = !blacklistAnalysis.get("black_top10_contact_creditcrack_count_ratio").isJsonNull() ?
                blacklistAnalysis.get("black_top10_contact_creditcrack_count_ratio").getAsDouble() : null;

        // 前10联系人近3月平均申请平台数
        JsonObject manyheadsAnalysis = data1.getAsJsonObject("contact_manyheads_analysis");
        String applyCount = !manyheadsAnalysis.get("manyheads_top10_contact_recent3month_partnercode_count_avg").isJsonNull() ?
                manyheadsAnalysis.get("manyheads_top10_contact_recent3month_partnercode_count_avg").getAsString() : null;
        //前10联系人近3月申请2个及以上平台的人数
        String applyCountOverTwo = !manyheadsAnalysis.get("manyheads_top10_contact_recent3month_partnercode_count_over2").isJsonNull() ?
                manyheadsAnalysis.get("manyheads_top10_contact_recent3month_partnercode_count_over2").getAsString() : null;

        userCarrier.setNetTime(mobileNetTime);
        userCarrier.setStatus(accountStatus);
        userCarrier.setAccountBalance(accountBalance);
        userCarrier.setIdentityCode(identityCode);
        userCarrier.setRealName(realName);
        userCarrier.setMobile(mobile);
        userCarrier.setMobileCarrier(mobileCarrier);
//        userCarrier.setInfo(reportData);
        if (mobileAddr == null) {
            mobileAddr = "";
        }
        userCarrier.setMobileAddr(mobileAddr.replaceAll("\\.", ""));
        userCarrier.setMutualNumber(mutualNumber);
        userCarrier.setBlackRadio(blackRadio);
        userCarrier.setBlackCreditRadio(blackCreditRadio);
        userCarrier.setApplyCount(applyCount);
        userCarrier.setApplyCountOverTwo(applyCountOverTwo);

        userCarrierRepository.saveAndFlush(userCarrier);
    }

    private void saveActiveSilenceStats(String orderId, JsonObject data1) {
        JsonObject jsonObject = data1.getAsJsonObject("active_silence_stats");
        ActiveSilenceStats activeSilenceStats = new ActiveSilenceStats();
        activeSilenceStats.setOrderId(orderId);
        if (jsonObject != null) {
            int activeDayCallThree = jsonObject.get("active_day_1call_3month")
                    .isJsonNull() ? jsonObject.get("active_day_1call_3month").getAsInt() : 0;
            int silenceDayCallThree = jsonObject.get("silence_day_0call_3month")
                    .isJsonNull() ? jsonObject.get("silence_day_0call_3month").getAsInt() : 0;
            int continueSilenceDayOverThree = jsonObject.get("continue_silence_day_over3_0call_3month")
                    .isJsonNull() ? jsonObject.get("continue_silence_day_over3_0call_3month").getAsInt() : 0;
            activeSilenceStats.setActiveDayCallThree(activeDayCallThree);
            activeSilenceStats.setSilenceDayCallThree(silenceDayCallThree);
            activeSilenceStats.setContinueSilenceDayOverThree(continueSilenceDayOverThree);
        }
        activeSilenceStatsRepository.save(activeSilenceStats);
    }

    private void saveFinanceContactDetail(String orderId, JsonObject data1) {

        JsonArray asJsonArray = data1.getAsJsonArray("finance_contact_detail");
        List<FinanceContactDetail> list = Lists.newArrayList();
        for (JsonElement jsonElement : asJsonArray) {
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            String contactNumber = !asJsonObject.get("contact_number").isJsonNull() ? asJsonObject.get("contact_number").getAsString() : null;
            String contactName = !asJsonObject.get("contact_name").isJsonNull() ? asJsonObject.get("contact_name").getAsString() : null;
            FinanceContactDetail financeContactDetail = new FinanceContactDetail();
            financeContactDetail.setOrderId(orderId);
            financeContactDetail.setContactNumber(contactNumber);
            financeContactDetail.setContactName(contactName);
            list.add(financeContactDetail);
        }
        financeContactDetailRepository.save(list);
    }

    private void saveFinanceContactStats(String orderId, JsonObject data1) {
        JsonArray asJsonArray = data1.getAsJsonArray("finance_contact_stats");
        List<FinanceContactStats> list = Lists.newArrayList();
        for (JsonElement jsonElement : asJsonArray) {
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            String contactType = !asJsonObject.get("contact_type").isJsonNull() ? asJsonObject.get("contact_type").getAsString() : null;
            int contactCount = !asJsonObject.get("contact_count_6month").isJsonNull() ? asJsonObject.get("contact_count_6month").getAsInt() : 0;
            int callCount = !asJsonObject.get("call_count_6month").isJsonNull() ? asJsonObject.get("call_count_6month").getAsInt() : 0;
            int callTime = !asJsonObject.get("call_time_6month").isJsonNull() ? asJsonObject.get("call_time_6month").getAsInt() : 0;
            FinanceContactStats financeContactStats = new FinanceContactStats();
            financeContactStats.setOrderId(orderId);
            financeContactStats.setContactType(contactType);
            financeContactStats.setContactCountSix(contactCount);
            financeContactStats.setCallCountSix(callCount);
            financeContactStats.setCallTimeSix(callTime);
            list.add(financeContactStats);
        }
        financeContactStatsRepository.save(list);
    }

    private void saveRiskContactDetail(String orderId, JsonObject data1) {
        JsonArray asJsonArray = data1.getAsJsonArray("risk_contact_detail");
        List<RiskContactDetail> list = Lists.newArrayList();
        for (JsonElement jsonElement : asJsonArray) {
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            String contactNumber = !asJsonObject.get("contact_number").isJsonNull() ? asJsonObject.get("contact_number").getAsString() : null;
            String contactName = !asJsonObject.get("contact_name").isJsonNull() ? asJsonObject.get("contact_name").getAsString() : null;
            RiskContactDetail riskContactDetail = new RiskContactDetail();
            riskContactDetail.setOrderId(orderId);
            riskContactDetail.setContactNumber(contactNumber);
            riskContactDetail.setContactName(contactName);
            list.add(riskContactDetail);
        }
        riskContactDetailRepository.save(list);
    }

    private void saveConsumeBill(String orderId, JsonObject data1) {
        JsonArray asJsonArray = data1.getAsJsonArray("carrier_consumption_stats_per_month");
        List<ConsumeBill> list = Lists.newArrayList();
        for (JsonElement jsonElement : asJsonArray) {
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            BigDecimal consumeAmount = asJsonObject.get("consume_amount").getAsBigDecimal().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal rechargeAmount = asJsonObject.get("recharge_amount").getAsBigDecimal().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
            String month = asJsonObject.get("month").getAsString();
            ConsumeBill consumeBill = new ConsumeBill();
            consumeBill.setOrderId(orderId);
            consumeBill.setConsumeAmount(consumeAmount);
            consumeBill.setMonth(month);
            consumeBill.setRechargeAmount(rechargeAmount);
            list.add(consumeBill);
        }
        consumeBillRepository.save(list);
    }

    private void saveRiskContactStats(String orderId, JsonObject data1) {
        JsonArray asJsonArray = data1.getAsJsonArray("risk_contact_stats");
        List<RiskContactStats> list = Lists.newArrayList();
        for (JsonElement jsonElement : asJsonArray) {
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            String riskType = !asJsonObject.get("risk_type").isJsonNull() ? asJsonObject.get("risk_type").getAsString() : null;
            int callCount = asJsonObject.get("call_count_6month").getAsInt();
            int callTime = asJsonObject.get("call_time_6month").getAsInt();
            int contactCount = asJsonObject.get("contact_count_6month").getAsInt();
            RiskContactStats riskContactStats = new RiskContactStats();
            riskContactStats.setOrderId(orderId);
            riskContactStats.setRiskType(riskType);
            riskContactStats.setCallCountSix(callCount);
            riskContactStats.setCallTimeSix(callTime);
            riskContactStats.setContactCountSix(contactCount);
            list.add(riskContactStats);
        }
        riskContactStatsRepository.save(list);
    }


    @Override
    public JsonObject magicReport(String taskId, Integer merchantId) throws IOException {
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("task_id", taskId));
        //获取运营商系统参数
        CarrierConfig carrierConfig = loanMarkConfigProvider.getCarrierConfig(merchantId);
        String code = carrierConfig.getPartnerCode();
        String key = carrierConfig.getPartnerKey();
        String url = "https://api.shujumohe.com/octopus/report.task.query/v2?partner_code=" + code + "&partner_key=" + key;
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(
                    EntityBuilder
                            .create()
                            .setContentType(ContentType.APPLICATION_FORM_URLENCODED).setContentEncoding("UTF-8")
                            .setParameters(parameters)
                            .build()
            );
            JsonObject jsonObject = httpClient.execute(httpPost, new JsonResponseHandler());
            if (jsonObject.get("code").getAsInt() == 0) {
                String jsonString = GZipUtils.uncompress(jsonObject.get("data").getAsString());
                JsonElement jsonElement = new JsonParser().parse(jsonString);
                jsonObject.add("data", jsonElement);
            }
            return jsonObject;
        }
    }

    @Override
    public UserCarrierVo carrierShow(String orderId) {
        UserCarrier userCarrier1 = userCarrierRepository.findByOrderId(orderId);
        ActiveSilenceStats activeSilenceStats1 = activeSilenceStatsRepository.findByOrderId(orderId);

        UserCarrierVo userCarrierVo = new UserCarrierVo();
        BeanUtils.copyProperties(userCarrier1,userCarrierVo);
        BeanUtils.copyProperties(activeSilenceStats1,userCarrierVo);
        return userCarrierVo;
    }



}
