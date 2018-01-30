package com.huotu.loanmarket.service.config;

import com.huotu.loanmarket.common.utils.StringUtilsExt;
import com.huotu.loanmarket.service.enums.ConfigParameter;
import com.huotu.loanmarket.service.model.SesameConfig;
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
        sesameConfig.setVerifyIdAndNameUrl(StringUtilsExt.safeGetMapValue(ConfigParameter.SesameParameter.NAME_AND_IDCARDNUM_URL.getKey(), map));
        return sesameConfig;
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