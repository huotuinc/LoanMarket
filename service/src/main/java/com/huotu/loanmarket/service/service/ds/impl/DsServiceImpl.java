package com.huotu.loanmarket.service.service.ds.impl;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.config.LoanMarkConfigProvider;
import com.huotu.loanmarket.service.entity.ds.AccountInfo;
import com.huotu.loanmarket.service.entity.ds.BaseInfo;
import com.huotu.loanmarket.service.entity.ds.DsOrder;
import com.huotu.loanmarket.service.entity.ds.Receiver;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;
import com.huotu.loanmarket.service.handler.JsonResponseHandler;
import com.huotu.loanmarket.service.model.CarrierConfig;
import com.huotu.loanmarket.service.model.ds.DsVo;
import com.huotu.loanmarket.service.repository.ds.AccountInfoRepository;
import com.huotu.loanmarket.service.repository.ds.BaseInfoRepository;
import com.huotu.loanmarket.service.repository.ds.DsOrderRepository;
import com.huotu.loanmarket.service.repository.ds.ReceiverRepository;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.service.ds.DsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 电商数据爬取服务
 * @author luyuanyuan on 2018/2/1.
 */
@Service
public class DsServiceImpl implements DsService {

    private static final Log log = LogFactory.getLog(DsServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BaseInfoRepository baseInfoRepository;
    @Autowired
    private AccountInfoRepository accountInfoRepository;
    @Autowired
    private DsOrderRepository dsOrderRepository;
    @Autowired
    private ReceiverRepository receiverRepository;

    private HttpClientBuilder httpClientBuilder;

    @PostConstruct
    public void init() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(30000).build();
        httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultRequestConfig(requestConfig).setUserAgent(Constant.DefaultAgent);
    }

    @Autowired
    private LoanMarkConfigProvider loanMarkConfigProvider;

    @Override
    @Transactional
    public ApiResult queryResult(String taskId, String orderId, Integer merchantId) throws IOException {
        //获取运营商系统参数
        CarrierConfig carrierConfig = loanMarkConfigProvider.getCarrierConfig(merchantId);
        String code = carrierConfig.getPartnerCode();
        String key = carrierConfig.getPartnerKey();
        String url = "https://api.shujumohe.com/octopus/task.unify.query/v3?partner_code=" + code + "&partner_key=" + key;

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
            Order order = orderRepository.findOne(orderId);
            order.setTaskId(taskId);
            int resultCode;
            if (operatorCode == 0) {
                //返回任务信息和原始数据
                JsonObject data = jsonObject.getAsJsonObject("data").getAsJsonObject("task_data");
//                String channelCode = data.get("channel_code").getAsString();
                assembleData(data,orderId,order);
                order.setAuthStatus(UserAuthorizedStatusEnums.AUTH_SUCCESS);
                order.getUser().setAuthStatus(UserAuthorizedStatusEnums.AUTH_SUCCESS);
                resultCode = AppCode.SUCCESS.getCode();
            } else {
                log.error(MessageFormat.format("【数据魔盒】电商数据查询失败，订单id：{0}，任务id：{1},失败原因：{2}", orderId,taskId,operatorMessage));
                order.setAuthStatus(UserAuthorizedStatusEnums.AUTH_ERROR);
                resultCode = AppCode.ERROR.getCode();
            }
            order.setAuthTime(LocalDateTime.now());
            orderRepository.saveAndFlush(order);
            return ApiResult.resultWith(resultCode,operatorMessage);
        }
    }

    private void assembleData(JsonObject data, String orderId,Order order) {
        //保存电商用户基本信息
        JsonObject baseInfoData = data.getAsJsonObject("base_info");
        if (baseInfoData != null){
            BaseInfo baseInfo = saveBaseInfo(baseInfoData, orderId);
            String accountNo = baseInfo.getName() + "（" + baseInfo.getNickName() + "）";
            order.setAccountName(accountNo);
            order.setAccountNo(accountNo);
        }
        //保存电商用户账户信息
        JsonObject accountInfoData = data.getAsJsonObject("account_info");
        if(accountInfoData != null) {
            saveAccountInfo(accountInfoData,orderId);
        }
        //保存电商订单
        JsonArray orderList = data.getAsJsonArray("order_list");
        saveDsOrder(orderList,orderId);
        //保存电商收货地址
        JsonArray receiverList = data.getAsJsonArray("receiver_list");
        saveReceiver(receiverList,orderId);
    }

    private void saveReceiver(JsonArray receiverList, String orderId) {
        List<Receiver> list = Lists.newArrayList();
        for (JsonElement jsonElement : receiverList) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String name = !jsonObject.get("name").isJsonNull()?jsonObject.get("name").getAsString():null;
            String area = !jsonObject.get("area").isJsonNull()?jsonObject.get("area").getAsString():null;
            String address = !jsonObject.get("address").isJsonNull()?jsonObject.get("address").getAsString():null;
            String mobile = !jsonObject.get("mobile").isJsonNull()?jsonObject.get("mobile").getAsString():null;
            Receiver receiver = new Receiver();
            receiver.setOrderId(orderId);
            receiver.setName(name);
            receiver.setArea(area);
            receiver.setAddress(address);
            receiver.setMobile(mobile);
            list.add(receiver);
        }
        receiverRepository.save(list);
    }

    private void saveDsOrder(JsonArray orderList, String orderId) {
        List<DsOrder> list = Lists.newArrayList();
        for (JsonElement jsonElement : orderList) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String dsOrderId = !jsonObject.get("order_id").isJsonNull()?jsonObject.get("order_id").getAsString():null;
            BigDecimal orderAmount = !jsonObject.get("order_amount").isJsonNull()?jsonObject.get("order_amount")
                    .getAsBigDecimal().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP):null;
            String orderType = !jsonObject.get("order_type").isJsonNull()?jsonObject.get("order_type").getAsString():null;
            String orderTime = !jsonObject.get("order_time").isJsonNull()?jsonObject.get("order_time").getAsString():null;
            String orderStatus = !jsonObject.get("order_status").isJsonNull()?jsonObject.get("order_status").getAsString():null;
            DsOrder dsOrder = new DsOrder();
            dsOrder.setOrderId(orderId);
            dsOrder.setDsOrderId(dsOrderId);
            dsOrder.setOrderAmount(orderAmount);
            dsOrder.setOrderType(orderType);
            dsOrder.setOrderTime(orderTime);
            dsOrder.setOrderStatus(orderStatus);
            list.add(dsOrder);
        }
        dsOrderRepository.save(list);
    }


    private void saveAccountInfo(JsonObject accountInfoData, String orderId) {
        BigDecimal accountBalance = !accountInfoData.get("account_balance").isJsonNull()?accountInfoData.get("account_balance")
                .getAsBigDecimal().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP):null;
        BigDecimal financialAccountBalance = !accountInfoData.get("financial_account_balance")
                .isJsonNull()?accountInfoData.get("financial_account_balance")
                .getAsBigDecimal().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP):null;

        String creditPoint = !accountInfoData.get("credit_point").isJsonNull()?accountInfoData.get("credit_point").getAsString():"未知";
        BigDecimal creditQuota = !accountInfoData.get("credit_quota").isJsonNull()?accountInfoData.get("credit_quota")
                .getAsBigDecimal().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP):null;
        BigDecimal consumeQuota = !accountInfoData.get("consume_quota").isJsonNull()?accountInfoData.get("consume_quota")
                .getAsBigDecimal().divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP):null;

        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setOrderId(orderId);
        accountInfo.setAccountBalance(accountBalance);
        accountInfo.setFinancialAccountBalance(financialAccountBalance);
        accountInfo.setCreditPoint(creditPoint);
        accountInfo.setCreditQuota(creditQuota);
        accountInfo.setConsumeQuota(consumeQuota);
        accountInfoRepository.save(accountInfo);

    }

    private BaseInfo saveBaseInfo(JsonObject baseInfoData, String orderId) {
        String userName = !baseInfoData.get("user_name").isJsonNull()?baseInfoData.get("user_name").getAsString():null;
        String email = !baseInfoData.get("email").isJsonNull()?baseInfoData.get("email").getAsString():null;
        String userLevel = !baseInfoData.get("user_level").isJsonNull()?baseInfoData.get("user_level").getAsString():null;
        String nickName = !baseInfoData.get("nick_name").isJsonNull()?baseInfoData.get("nick_name").getAsString():null;
        String name = !baseInfoData.get("name").isJsonNull()?baseInfoData.get("name").getAsString():null;
        String gender = !baseInfoData.get("gender").isJsonNull()?baseInfoData.get("gender").getAsString():null;
        String mobile = !baseInfoData.get("mobile").isJsonNull()?baseInfoData.get("mobile").getAsString():null;
        String realName = !baseInfoData.get("real_name").isJsonNull()?baseInfoData.get("real_name").getAsString():null;
        String identityCode = !baseInfoData.get("identity_code").isJsonNull()?baseInfoData.get("identity_code").getAsString():null;

        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setOrderId(orderId);
        baseInfo.setEmail(email);
        baseInfo.setGender(gender);
        baseInfo.setIdentityCode(identityCode);
        baseInfo.setMobile(mobile);
        baseInfo.setName(name);
        baseInfo.setRealName(realName);
        baseInfo.setNickName(nickName);
        baseInfo.setUserLevel(userLevel);
        baseInfo.setUserName(userName);
        return baseInfoRepository.save(baseInfo);
    }

    @Override
    public DsVo dsShow(String orderId) {
        BaseInfo baseInfo = baseInfoRepository.findByOrderId(orderId);
        AccountInfo accountInfo = accountInfoRepository.findByOrderId(orderId);

        DsVo dsVo = new DsVo();
        BeanUtils.copyProperties(baseInfo,dsVo);
        BeanUtils.copyProperties(accountInfo,dsVo);
        return dsVo;
    }
}
