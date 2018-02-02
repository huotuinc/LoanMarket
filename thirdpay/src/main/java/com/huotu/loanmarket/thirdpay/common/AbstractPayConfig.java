/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.thirdpay.common;


import lombok.Getter;
import lombok.Setter;

/**
 * 支付配置信息抽象类
 * @author wm
 */
@Getter
@Setter
public abstract class AbstractPayConfig {
    /**
     * 得到回调地址
     * @return
     */
    public abstract String getReturnUrl(String unifiedOrderNo);
    /**
     * 得到H5支付的地址
     * @return
     */
    public abstract String getWapPayUrl(String unifiedOrderNo);
    /**
     * 得到异步通知的地址
     * @return
     */
    public abstract String getNotifyUrl();
    /**
     * 根目录地址
     */
    public String rootDomain;
}