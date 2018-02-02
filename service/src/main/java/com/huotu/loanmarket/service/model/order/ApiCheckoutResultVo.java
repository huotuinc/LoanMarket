/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.model.order;

import com.huotu.loanmarket.service.model.payconfig.ApiPaymentVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 结账支付页实体
 * @author wm
 */
@Getter
@Setter
public class ApiCheckoutResultVo {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 要付的金额
     */
    private BigDecimal finalAmount;
    /**
     * 支持的支付方式
     */
    private List<ApiPaymentVo> payments;
    /**
     * 交易名称
     */
    private String tradeName;
}