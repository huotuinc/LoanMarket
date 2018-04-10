package com.huotu.loanmarket.webapi.controller.sesame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.domain.ZmWatchListDetail;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditAntifraudVerifyRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditWatchlistiiGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditAntifraudVerifyResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditWatchlistiiGetResponse;
import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.enums.EnumHelper;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.common.utils.HttpUtils;
import com.huotu.loanmarket.common.utils.RandomUtils;
import com.huotu.loanmarket.service.config.LoanMarkConfigProvider;
import com.huotu.loanmarket.service.config.SesameSysConfig;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.entity.sesame.Industry;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.*;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.model.sesame.BizParams;
import com.huotu.loanmarket.service.model.sesame.IdentityParam;
import com.huotu.loanmarket.service.model.sesame.SesameConfig;
import com.huotu.loanmarket.service.service.order.OrderService;
import com.huotu.loanmarket.service.service.sesame.SesameService;
import com.huotu.loanmarket.service.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.awt.ModalExclude;
import sun.awt.RepaintArea;

import javax.xml.transform.Source;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hxh
 * @Date 2018/1/30 11:21
 */
@Controller
@RequestMapping("/api/sesame")
public class SesameController {
    private static final Log log = LogFactory.getLog(SesameController.class);

    @Autowired
    private SesameService sesameService;
    @Autowired
    private LoanMarkConfigProvider loanMarkConfigProvider;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping("/verifyIdAndName")
    @ResponseBody
    public ApiResult verifyIdAndName(@RequestHeader(Constant.APP_MERCHANT_ID_KEY) Integer merchantId,
                                     @RequestHeader(value = Constant.APP_USER_ID_KEY, required = false) Long userId, String name, String idCardNum) {
        log.info("用户欺诈信息开始芝麻授权userId：" + userId);
        String host = "http://idcard.market.alicloudapi.com";
        String path = "/lianzhuo/idcard";
        String method = "GET";
        String appcode = "f3c92fddc07c4470ab2bd0590a8e359f";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("cardno", idCardNum);
        querys.put("name", name);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            //获取response的body
            String retSrc = EntityUtils.toString(response.getEntity());
            JSONObject result = JSON.parseObject(retSrc);
            JSONObject respJson = (JSONObject) result.get("resp");
            int code = (int) respJson.get("code");
            if (code == 0) {
                return ApiResult.resultWith(AppCode.SUCCESS);
            }
            return ApiResult.resultWith(SesameResultCode.NAME_AND_NUM_NOT_AGREEMENT);
        } catch (Exception e) {
            log.info("实名认证异常：" + e);
            return ApiResult.resultWith(SesameResultCode.NAME_AND_NUM_NOT_AGREEMENT);
        }

    }

    /**
     * 获取授权地址（已修改，仅供测试）
     *
     * @param merchantId 商户编号
     * @param userId     用户编号
     * @return
     */
    @RequestMapping(value = "/getSesameUrl", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getAuthenticationUrl(@RequestHeader(Constant.APP_USER_ID_KEY) Long userId
            , @RequestHeader(Constant.APP_MERCHANT_ID_KEY) Integer merchantId, String name, String orderId, String idCardNum) throws ErrorMessageException {
        log.info("用户行业黑名单开始芝麻授权userId：" + userId);
        ZhimaAuthInfoAuthorizeRequest req = new ZhimaAuthInfoAuthorizeRequest();
        // 必要参数
        req.setChannel("apppc");
        req.setPlatform("zmop");
        req.setIdentityType("2");
        IdentityParam identityParam = new IdentityParam();
        identityParam.setCertNo(idCardNum);
        identityParam.setName(name);
        req.setIdentityParam(identityParam.toString());
        BizParams bizParams = new BizParams();
        bizParams.setState(userId + "," + orderId);
        req.setBizParams(bizParams.toString());
        //读取系统参数
        SesameConfig sesameConfig = loanMarkConfigProvider.getSesameConfig(merchantId);
        DefaultZhimaClient client = new DefaultZhimaClient(SesameSysConfig.SESAME_CREDIT_URL, sesameConfig.getAppId(),
                sesameConfig.getPrivateKey().trim(),
                sesameConfig.getPublicKey().trim());
        try {
            String url = client.generatePageRedirectInvokeUrl(req);
            return ApiResult.resultWith(AppCode.SUCCESS.getCode(), "芝麻信用授权成功", url);
        } catch (ZhimaApiException e) {
            return null;
        }
    }

    /**
     * 芝麻信用回调
     *
     * @return
     */
    @RequestMapping("/rollBack/{merchantId}")
    public String rollBack(@PathVariable("merchantId") Integer merchantId, String params, String sign, Model model) throws ErrorMessageException {
        log.info(MessageFormat.format("【芝麻信用(行业黑名单)】回调开始，params：{0}，sign：{1}", params, sign));
        try {
            //参数解密
            if (params.contains("%")) {
                params = URLDecoder.decode(params, Constant.ENCODING_UTF8);
            }
            if (sign.contains("%")) {
                sign = URLDecoder.decode(sign, Constant.ENCODING_UTF8);
            }
            //读取第三方配置参数
            SesameConfig sesameConfig = loanMarkConfigProvider.getSesameConfig(merchantId);
            DefaultZhimaClient client = new DefaultZhimaClient(SesameSysConfig.SESAME_CREDIT_URL, sesameConfig.getAppId(),
                    sesameConfig.getPrivateKey().trim(),
                    sesameConfig.getPublicKey().trim());
            String result = client.decryptAndVerifySign(params, sign);
            result = URLDecoder.decode(result, Constant.ENCODING_UTF8);
            log.info("result:" + result);
            //获取open_id和state头传参数（记录userId和orderId）
            String[] strings = result.split("&");
            Map<String, String> map = new HashMap<>();
            for (String str : strings) {
                String[] split = str.split("=");
                map.put(split[0], split[1]);
            }
            String info = map.get("state");
            Long userId = Long.parseLong(info.split(",")[0]);
            String orderId = info.split(",")[1];
            ZhimaCreditWatchlistiiGetRequest req = new ZhimaCreditWatchlistiiGetRequest();
            req.setChannel("apppc");
            req.setPlatform("zmop");
            req.setProductCode(SesameSysConfig.PRODUCT_CODE_SCORE);
            req.setTransactionId(RandomUtils.getTransactionId());
            req.setOpenId(map.get("open_id"));
            ZhimaCreditWatchlistiiGetResponse response = client.execute(req);
            Order order = orderService.findByOrderId(orderId);
            //1.修改订单状态 2.保存行业名单信息 3.更新用户认账状态 4.设置信用估值
            if (response.isSuccess()) {
                order.getUser().setAuthStatus(UserAuthorizedStatusEnums.AUTH_SUCCESS);
                order.setAuthStatus(UserAuthorizedStatusEnums.AUTH_SUCCESS);
                order.setAuthTime(LocalDateTime.now());
                //先设置邀请状态
                orderService.updateInviteStatus(userId);
                List<ZmWatchListDetail> details = response.getDetails();
                if (details == null || details.size() == 0) {
                    orderService.save(order);
                    log.info(MessageFormat.format("初始化信用估值，userId：{0}，订单类型：{1}", userId, OrderEnum.OrderType.BACKLIST_BUS.getName()));
                    userService.updateUserCreditValue(userId, OrderEnum.OrderType.BACKLIST_BUS);
                    //如果该用户是被邀请的，更新邀请表状态
                    model.addAttribute("order", order);
                    return "sesame/sesame_success";
                }
                details.forEach(detail -> {
                    Industry industry = new Industry();
                    industry.setOrderId(orderId);
                    industry.setUserId(userId);
                    industry.setRankCode(EnumHelper.getEnumType(SesameEnum.RankCode.class, detail.getCode()));
                    industry.setBiz_code(EnumHelper.getEnumType(SesameEnum.IndustryType.class, detail.getBizCode()));
                    industry.setCode(EnumHelper.getEnumType(SesameEnum.RankNumber.class, detail.getType()));
                    industry.setStatus(EnumHelper.getEnumType(SesameEnum.Status.class, detail.getStatus()));
                    industry.setStatement(detail.getStatement());
                    industry.setLevel(EnumHelper.getEnumType(SesameEnum.Level.class, detail.getLevel().toString()));
                    if (detail.getSettlement()) {
                        industry.setSettlement(SesameEnum.Settlement.SETTLED);
                    } else {
                        industry.setSettlement(SesameEnum.Settlement.UNSETTLED);
                    }
                    sesameService.save(industry);
                });
            } else {
                order.setAuthStatus(UserAuthorizedStatusEnums.AUTH_ERROR);
            }
            orderService.save(order);
            if (response.isSuccess()) {
                log.info(MessageFormat.format("初始化信用估值，userId：{0}，订单类型：{1}", userId, OrderEnum.OrderType.BACKLIST_BUS.getName()));
                userService.updateUserCreditValue(userId, OrderEnum.OrderType.BACKLIST_BUS);
                model.addAttribute("order", order);
                return "sesame/sesame_success";
            } else {
                model.addAttribute("errorMsg", response.getErrorMessage());
                return "sesame/sesame_error";
            }
        } catch (Exception e) {
            log.error("芝麻回调异常" + e);
            return "sesame/sesame_error";
        }
    }


}
