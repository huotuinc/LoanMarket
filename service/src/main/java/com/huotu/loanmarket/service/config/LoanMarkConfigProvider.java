package com.huotu.loanmarket.service.config;

import com.huotu.loanmarket.common.utils.StringUtilsExt;
import com.huotu.loanmarket.service.enums.ConfigParameter;
import com.huotu.loanmarket.service.model.CarrierConfig;
import com.huotu.loanmarket.service.model.sesame.SesameConfig;
import com.huotu.loanmarket.service.service.merchant.MerchantCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 借条系统配置负责人
 * 要什么配置都跟我要
 *
 * @author wm
 */
@Component
public class LoanMarkConfigProvider {
    @Autowired
    private MerchantCfgService merchantCfgService;

    @Autowired
    private Environment environment;


    public SesameConfig getSesameConfig(Integer merchantId) {
        Map<String, String> map = this.getMerchantConfigParameters(merchantId);
        SesameConfig sesameConfig = new SesameConfig();
        sesameConfig.setApiId(StringUtilsExt.safeGetMapValue(ConfigParameter.SesameParameter.API_ID.getKey(), map));
        sesameConfig.setApiSecret(StringUtilsExt.safeGetMapValue(ConfigParameter.SesameParameter.API_SECRET.getKey(), map));
        sesameConfig.setAppId(StringUtilsExt.safeGetMapValue(ConfigParameter.SesameParameter.APP_ID.getKey(), map));
        sesameConfig.setPublicKey(StringUtilsExt.safeGetMapValue(ConfigParameter.SesameParameter.PUBLIC_KEY.getKey(), map));
        sesameConfig.setPrivateKey(StringUtilsExt.safeGetMapValue(ConfigParameter.SesameParameter.PRIVATE_KEY.getKey(), map));
        sesameConfig.setAppCheatId(StringUtilsExt.safeGetMapValue(ConfigParameter.SesameParameter.CHEAT_APP_ID.getKey(), map));
        sesameConfig.setPublicCheatKey(StringUtilsExt.safeGetMapValue(ConfigParameter.SesameParameter.CHEAT_PUBLIC_KEY.getKey(), map));
        sesameConfig.setPrivateCheatKey(StringUtilsExt.safeGetMapValue(ConfigParameter.SesameParameter.CHEAT_PRIVATE_KEY.getKey(), map));
        return sesameConfig;
    }

    public CarrierConfig getCarrierConfig(Integer merchantId) {
        Map<String, String> map = this.getMerchantConfigParameters(merchantId);
        CarrierConfig carrierConfig = new CarrierConfig();
        carrierConfig.setPartnerCode(StringUtilsExt.safeGetMapValue(ConfigParameter.CarrierParameter.PARTNER_CODE.getKey(), map, "guohaiyx_mohe"));
        carrierConfig.setPartnerKey(StringUtilsExt.safeGetMapValue(ConfigParameter.CarrierParameter.PARTNER_KEY.getKey(), map, "f00229ea8eb349e68db729db6e757c65"));
        return carrierConfig;
    }

    private Pattern rechargeRangePattern = Pattern.compile("\\[([^\\-]+)-([^\\]]+)\\]\\s*=\\s*([^;]+);");


    //region 私有方法
    private BigDecimal parseBigDecimal(String a, BigDecimal defaultValue) {
        if (StringUtils.isEmpty(a)) {
            return defaultValue;
        }
        try {
            return new BigDecimal(a);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    private Integer parseInteger(String a, Integer defaultValue) {
        if (StringUtils.isEmpty(a)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(a);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    private List<Integer> convertToList(String ruleIds) {
        List<Integer> list = new ArrayList<>();
        if (!StringUtils.isEmpty(ruleIds)) {
            try {
                String[] arrRulesIds = ruleIds.split(",");
                for (String id : arrRulesIds) {
                    if (!StringUtils.isEmpty(id)) {
                        list.add(new Integer(id));
                    }
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    private Map<String, String> getMerchantConfigParameters(Integer merchantId) {
        return merchantCfgService.getConfigItem(merchantId);
    }
    //endregion
}