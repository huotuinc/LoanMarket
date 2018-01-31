package com.huotu.loanmarket.webapi.controller.sesame;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.domain.ZmWatchListDetail;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditScoreGetRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditWatchlistiiGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditWatchlistiiGetResponse;
import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.common.utils.RandomUtils;
import com.huotu.loanmarket.service.config.LoanMarkConfigProvider;
import com.huotu.loanmarket.service.config.SesameSysConfig;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.SesameResultCode;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.model.sesame.BizParams;
import com.huotu.loanmarket.service.model.sesame.IdentityParam;
import com.huotu.loanmarket.service.model.sesame.SesameConfig;
import com.huotu.loanmarket.service.service.sesame.SesameService;
import com.huotu.loanmarket.service.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private UserService userService;
    @Autowired
    private SesameService sesameService;
    @Autowired
    private LoanMarkConfigProvider loanMarkConfigProvider;

    @RequestMapping("/verifyIdAndName")
    @ResponseBody
    public ApiResult verifyIdAndName(@RequestHeader(Constant.APP_MERCHANT_ID_KEY) Integer merchantId,
                                     @RequestHeader(value = Constant.APP_USER_ID_KEY, required = false) Long userId, String name, String idCardNum) {
        if (userId == null || userId == 0) {
            return ApiResult.resultWith(SesameResultCode.USERID_EMPTY);
        }
        User user = userService.findByMerchantIdAndUserId(merchantId, userId);
        if (user == null) {
            return ApiResult.resultWith(SesameResultCode.USER_EMPTY);
        }
        //判断name和idCardNum是否匹配
        boolean result = sesameService.checkNameAndIdCardNum(merchantId, name, idCardNum);
        if (result) {
            return ApiResult.resultWith(AppCode.SUCCESS);
        }
        return ApiResult.resultWith(SesameResultCode.NAME_AND_NUM_NOT_AGREEMENT);
    }

    /**
     * 获取授权地址
     *
     * @param merchantId 商户编号
     * @param userId     用户编号
     * @return
     */
    @RequestMapping(value = "/getSesameUrl", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getAuthenticationUrl(@RequestHeader(Constant.APP_USER_ID_KEY) Long userId
            , @RequestHeader(Constant.APP_MERCHANT_ID_KEY) int merchantId, String name, String idCardNum) throws ErrorMessageException {
        if (userId == null || userId == 0) {
            return ApiResult.resultWith(SesameResultCode.USERID_EMPTY);
        }
        User user = userService.findByMerchantIdAndUserId(merchantId, userId);
        if (user == null) {
            return ApiResult.resultWith(SesameResultCode.USER_EMPTY);
        }
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
        bizParams.setState(userId + "," + merchantId);
        req.setBizParams(bizParams.toString());
        //读取系统参数
        SesameConfig sesameConfig = loanMarkConfigProvider.getSesameConfig(merchantId);
        DefaultZhimaClient client = new DefaultZhimaClient(SesameSysConfig.SESAME_CREDIT_URL, sesameConfig.getAppId(),
                sesameConfig.getPrivateKey().trim(),
                sesameConfig.getPublicKey().trim());
        try {
            String url = client.generatePageRedirectInvokeUrl(req);
            System.out.println(url);
            return ApiResult.resultWith(AppCode.SUCCESS.getCode(), "芝麻信用授权成功", url);
        } catch (ZhimaApiException e) {
            return null;
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            //发送邮件
//            HybridPageConfig hybridPageConfig = superloanConfigProvider.getHybridPageConfig(merchantId);
//            String emails = hybridPageConfig.getReceiveEmailUserName().trim();
//            try {
//                emailService.send(merchantId, emails, "报警信息-芝麻信用获取授权地址"
//                        , MessageFormat.format("获取芝麻授权路径时发生错误，userId：{0}，异常信息：{1}"
//                                , userId, sw.toString()));
//            } catch (Exception e1) {
//                log.error("发送报警邮件发生错误:" + e1);
//            }
//            log.error("芝麻信用获取授权路径异常：" + e);
//            throw new ErrorMessageException(AuthenticationResultCode.SESAME_AUTHENTICATION_ERROR);
        }
    }

    /**
     * 芝麻信用回调
     *
     * @return
     */
    @RequestMapping("/rollBack/{merchantId}")
    public String rollBack(@PathVariable("merchantId") Integer merchantId, String params, String sign) throws ErrorMessageException {
        log.info(MessageFormat.format("【芝麻信用】回调开始，params：{0}，sign：{1}", params, sign));
        String verfiResult = null;
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
            //获取open_id和state头传参数（记录userId和merchantId）
            String[] strings = result.split("&");
            Map<String, String> map = new HashMap<>();
            for (String str : strings) {
                String[] split = str.split("=");
                map.put(split[0], split[1]);
            }
            ZhimaCreditWatchlistiiGetRequest req = new ZhimaCreditWatchlistiiGetRequest();
            req.setChannel("apppc");
            req.setPlatform("zmop");
            req.setProductCode(SesameSysConfig.PRODUCT_CODE_SCORE);
            req.setTransactionId(RandomUtils.getTransactionId());
            req.setOpenId(map.get("open_id"));
            ZhimaCreditWatchlistiiGetResponse response = (ZhimaCreditWatchlistiiGetResponse) client.execute(req);
            if (response.isSuccess()) {

                List<ZmWatchListDetail> details = response.getDetails();
                details.forEach(detail->{
                    String s = detail.toString();
                    System.out.println(s);
                });
                return "unifiedorder/sesame_return";
            }
            return null;
        } catch (Exception e) {
            return null;
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            //发送邮件
//            HybridPageConfig hybridPageConfig = superloanConfigProvider.getHybridPageConfig(merchantId);
//            String emails = hybridPageConfig.getReceiveEmailUserName().trim();
//            try {
//                emailService.send(merchantId, emails, "报警信息-芝麻回调"
//                        , MessageFormat.format("芝麻信用回调时发生错误，params：{0}，sign：{1}，异常信息：{2}"
//                                , params, sign, sw.toString()));
//            } catch (Exception e1) {
//                log.error("发送报警邮件发生错误:" + e1);
//            }
//            log.error("芝麻认证(回调)异常：" + e);
//            throw new ErrorMessageException(AuthenticationResultCode.SESAME_AUTHENTICATION_ERROR.getValue(), e.getMessage());
        }
    }
}
