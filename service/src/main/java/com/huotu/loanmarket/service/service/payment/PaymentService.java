/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.payment;

import com.huotu.loanmarket.service.entity.payment.PaymentCfg;
import com.huotu.loanmarket.service.model.payconfig.ApiPaymentVo;

import java.util.List;

/**
 * @author guomw
 * @date 02/02/2018
 */
public interface PaymentService {
    /**
     * 获取支付方式
     *
     * @param merchantId
     * @return
     */
    List<PaymentCfg> getByMerchantId(Integer merchantId);

    /**
     * 获取支付方式
     *
     * @param paymentId
     * @param merchantId
     * @return
     */
    PaymentCfg getByPaymentIdAndMerchantId(Long paymentId, Integer merchantId);

    /**
     * 获取可用的经过排序的支付方式
     * @param merchantId 商家id
     * @return 可用支付方式
     */
    List<ApiPaymentVo> getAvailablePaymentList(Integer merchantId);
}
