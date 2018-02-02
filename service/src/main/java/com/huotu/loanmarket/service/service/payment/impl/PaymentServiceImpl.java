/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.payment.impl;

import com.huotu.loanmarket.service.entity.payment.PaymentCfg;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.model.payconfig.ApiPaymentVo;
import com.huotu.loanmarket.service.repository.payment.PaymentRepository;
import com.huotu.loanmarket.service.service.payment.PaymentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guomw
 * @date 02/02/2018
 */
@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Log log = LogFactory.getLog(PaymentServiceImpl.class);
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<PaymentCfg> getByMerchantId(Integer merchantId) {
        return paymentRepository.findByMerchantIdOrderBySortNumDesc(merchantId);
    }

    @Override
    public PaymentCfg getByPaymentIdAndMerchantId(Long paymentId, Integer merchantId) {
        return paymentRepository.findByPaymentIdAndMerchantId(paymentId, merchantId);
    }

    @Override
    public List<ApiPaymentVo> getAvailablePaymentList(Integer merchantId) {
        List<PaymentCfg> paymentCfgList = paymentRepository.findAvailableList(merchantId);
        List<ApiPaymentVo> apiPaymentVoList = new ArrayList<>();
        for (PaymentCfg paymentCfg : paymentCfgList) {
            ApiPaymentVo paymentVo = new ApiPaymentVo();
            paymentVo.setName(paymentCfg.getPayName());
            paymentVo.setPayType(paymentCfg.getPayType().getCode());
            paymentVo.setRemark(paymentCfg.getRemark());
            apiPaymentVoList.add(paymentVo);
        }
        return apiPaymentVoList;
    }
}
