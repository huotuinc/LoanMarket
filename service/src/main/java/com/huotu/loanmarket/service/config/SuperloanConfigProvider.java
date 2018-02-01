/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.config;

import com.huotu.loanmarket.common.utils.StringUtilsExt;
import com.huotu.loanmarket.service.enums.ConfigParameter;
import com.huotu.loanmarket.service.service.BaseService;
import com.huotu.loanmarket.service.service.merchant.MerchantCfgService;
import com.huotu.loanmarket.thirdpay.alipay.model.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 借条系统配置负责人
 * 要什么配置都跟我要
 *
 * @author wm
 */
@Component
public class SuperloanConfigProvider {
    @Autowired
    private MerchantCfgService merchantCfgService;

    @Autowired
    private Environment environment;
    @Autowired
    private BaseService baseService;

    /**
     * 得到支付宝配置参数
     *
     * @param merchantId 商家id
     * @return
     */
    public AlipayConfig getAlipayConfig(Integer merchantId) {
        Map<String, String> map = this.getMerchantConfigParameters(merchantId);
        AlipayConfig alipayConfig = new AlipayConfig(baseService.apiHomeURI());
        alipayConfig.setAppId(StringUtilsExt.safeGetMapValue(ConfigParameter.AlipayParameter.ALIPAY_APPID.getKey(), map));
        alipayConfig.setPrivateKey(StringUtilsExt.safeGetMapValue(ConfigParameter.AlipayParameter.ALIPAY_RSA_PRIVAT_EKEY.getKey(), map));
        alipayConfig.setAliPayPublicKey(StringUtilsExt.safeGetMapValue(ConfigParameter.AlipayParameter.ALIPAY_PUBLIC_KEY.getKey(), map));
        return alipayConfig;
    }


    private Map<String, String> getMerchantConfigParameters(Integer merchantId) {
        return merchantCfgService.getConfigItem(merchantId);
    }
}