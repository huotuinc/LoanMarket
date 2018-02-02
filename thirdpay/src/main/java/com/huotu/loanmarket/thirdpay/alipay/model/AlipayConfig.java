/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.thirdpay.alipay.model;

import com.huotu.loanmarket.thirdpay.common.AbstractPayConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * 支付宝的配置信息
 *
 * @author wm
 */
@Getter
@Setter
public class AlipayConfig extends AbstractPayConfig {
    public AlipayConfig(String rootDomain) {
        this.rootDomain = rootDomain;
    }

    /**
     * 应用id
     */
    private String appId;
    /**
     * 私钥文本信息
     */
    private String privateKey;
    /**
     * 支付宝公约文本信息
     */
    private String aliPayPublicKey;

    @Override
    public String getReturnUrl(String unifiedOrderNo) {
        //注：支付宝的returnurl不允许带任何参数
        return this.rootDomain + "api/alipay/return/order-" + unifiedOrderNo;
    }

    @Override
    public String getWapPayUrl(String unifiedOrderNo) {
        return this.rootDomain + "api/alipay/redirect/order-" + unifiedOrderNo;
    }

    @Override
    public String getNotifyUrl() {
        return this.rootDomain + "api/alipay/notify";
    }
}