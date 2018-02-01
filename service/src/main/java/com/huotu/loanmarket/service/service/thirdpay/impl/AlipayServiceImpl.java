/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.thirdpay.impl;

import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.config.SuperloanConfigProvider;
import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.model.payconfig.PaymentBizParametersVo;
import com.huotu.loanmarket.service.service.thirdpay.QuickPaymentService;
import com.huotu.loanmarket.thirdpay.alipay.model.AlipayConfig;
import com.huotu.loanmarket.thirdpay.alipay.service.AlipayHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 快捷支付-支付宝相关信息提供人
 *
 * @author wm
 */
@Service
public class AlipayServiceImpl implements QuickPaymentService<AlipayConfig> {
    @Autowired
    private SuperloanConfigProvider superloanConfigProvider;

    @Override
    public AlipayConfig getPayConfig(Integer merchantId) {
        return superloanConfigProvider.getAlipayConfig(merchantId);
    }

    @Override
    public PaymentBizParametersVo getBizParameters(Order unifiedOrder) {
        AlipayConfig alipayConfig = this.getPayConfig(Constant.MERCHANT_ID);
        if (alipayConfig == null) {
            return null;
        }
        PaymentBizParametersVo bizParametersVo = new PaymentBizParametersVo();
        bizParametersVo.setReturnUrl(alipayConfig.getReturnUrl(unifiedOrder.getOrderId()));
        bizParametersVo.setWapPayUrl(alipayConfig.getWapPayUrl(unifiedOrder.getOrderId()));
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setTimeoutExpress("30m");
        model.setSubject(unifiedOrder.getOrderId());
        model.setOutTradeNo(unifiedOrder.getOrderId());
        model.setTotalAmount(unifiedOrder.getPayAmount().toString());
        String bizPackage = AlipayHelper.buildAppPayBizPackage(alipayConfig, model, alipayConfig.getNotifyUrl(), bizParametersVo.getReturnUrl());
        bizParametersVo.setBizPackage(bizPackage);
        return bizParametersVo;
    }

    @Override
    public OrderEnum.PayType getType() {
        return OrderEnum.PayType.ALIPAY;
    }


}