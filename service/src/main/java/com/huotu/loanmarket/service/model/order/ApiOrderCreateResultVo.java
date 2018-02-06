/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.model.order;

import com.huotu.loanmarket.service.model.payconfig.PaymentBizParametersVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 订单创建接口返回结果
 * @author wm
 */
@Getter
@Setter
public class ApiOrderCreateResultVo {
    /**
     * 单号
     */
    private String orderNo;
    /**
     * 第三方支付方式
     */
    private Integer payType;
    /**
     * 交易类型
     */
    private Integer tradeType;
    /**
     * 剩余需要支付的金额
     */
    private BigDecimal surplusAmount;
    /**
     * 第三方授权地址
     */
    private String thirdAuthUrl;
    /**
     * 一些支付要用到的业务参数
     */
    private PaymentBizParametersVo bizParameters;
}