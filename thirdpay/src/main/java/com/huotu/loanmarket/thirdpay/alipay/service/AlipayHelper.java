/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.thirdpay.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.huotu.loanmarket.thirdpay.alipay.model.AlipayConfig;

import java.util.Map;


/**
 * 支付宝助手类
 * @author wm
 */
public class AlipayHelper {
    private static final String SIGN_TYPE  = "RSA2";
    private static final String CHARSET = "UTF-8";
    private static final String FORMAT = "json";
    private static final String GATEWAY_URL="https://openapi.alipay.com/gateway.do";

    /**
     * 生成APP端需要用到的交易数据
     * @param payConfig
     * @param bizModel
     * @param notifyUrl
     * @param returnUrl
     * @return
     */
    public static String buildAppPayBizPackage(AlipayConfig payConfig, AlipayTradeAppPayModel bizModel,String notifyUrl,String returnUrl){
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, payConfig.getAppId(), payConfig.getPrivateKey(), FORMAT, CHARSET, payConfig.getAliPayPublicKey(), SIGN_TYPE);
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(bizModel);
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);
        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 生成H5端需要用到的交易数据
     * @param payConfig
     * @param bizModel
     * @param notifyUrl
     * @param returnUrl
     * @return
     */
    public static  String buildWapPayBizPackage(AlipayConfig payConfig, AlipayTradeWapPayModel bizModel,String  notifyUrl,String returnUrl) {
        AlipayClient client = new DefaultAlipayClient(GATEWAY_URL, payConfig.getAppId(), payConfig.getPrivateKey(), FORMAT, CHARSET, payConfig.getAliPayPublicKey(), SIGN_TYPE);
        AlipayTradeWapPayRequest request=new AlipayTradeWapPayRequest();
        // 封装请求支付信息
        request.setBizModel(bizModel);
        // 设置异步通知地址
        request.setNotifyUrl(notifyUrl);
        // 设置同步地址
        request.setReturnUrl(returnUrl);
        try {
            return client.pageExecute(request).getBody();
        }
        catch (AlipayApiException e) {

            e.printStackTrace();
        }
        return "";
    }

    /**
     * 验签
     * @param params
     * @param publicKey
     * @return
     * @throws AlipayApiException
     */
    public static boolean rsaCheck(Map<String, String> params, String publicKey) throws AlipayApiException {
        return AlipaySignature.rsaCheckV1(params, publicKey, CHARSET,SIGN_TYPE);
    }
}