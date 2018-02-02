/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.thirdpay;

import com.huotu.loanmarket.service.entity.order.Order;
import com.huotu.loanmarket.service.enums.OrderEnum;
import com.huotu.loanmarket.service.model.payconfig.PaymentBizParametersVo;
import com.huotu.loanmarket.thirdpay.common.AbstractPayConfig;

/**
 * 第三方快捷支付接口
 * @author wm
 */
public interface QuickPaymentService<T extends AbstractPayConfig> {
    /**
     * 得到商家支付秘钥参数
     * @param merchantId
     * @return
     */
    T getPayConfig(Integer merchantId);

    /**
     * 得到支付的业务参数
     * 各支付方式都必须在服务端完成签名等操作
     * @param order
     * @return
     */
    PaymentBizParametersVo getBizParameters(Order order);

    /**
     * 获取快捷支付类型
     * @return
     */
    OrderEnum.PayType getType();
}