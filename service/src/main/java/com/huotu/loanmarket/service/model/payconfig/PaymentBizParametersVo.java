/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.model.payconfig;

import lombok.Getter;
import lombok.Setter;

/**
 * 第三方支付业务参数对象
 * @author wm
 */
@Getter
@Setter
public class PaymentBizParametersVo {
    public PaymentBizParametersVo(){

    }
    /**
     * 支付完成后去的页面
     */
    private String returnUrl;
    /**
     * 使用H5支付跳转去的网址
     */
    private String wapPayUrl;
    /**
     * app发起支付请求用到的包裹
     * 包含订单信息，按规定签名
     */
    private String bizPackage;
}