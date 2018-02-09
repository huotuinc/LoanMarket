package com.huotu.loanmarket.service.config;

import com.huotu.loanmarket.common.utils.StringUtilsExt;
import com.huotu.loanmarket.service.enums.ConfigParameter;
import com.huotu.loanmarket.service.model.CarrierConfig;
import com.huotu.loanmarket.service.model.sesame.SesameConfig;
import com.huotu.loanmarket.service.model.tongdun.TongdunApiConfig;
import com.huotu.loanmarket.service.model.tongdun.TongdunRiskRuleIdConfig;
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

    /**
     * 得到同盾接口参数
     *
     * @param merchantId 商家id
     * @return
     */
    public TongdunApiConfig getTongdunApiConfig(Integer merchantId) {
        Map<String, String> map = this.getMerchantConfigParameters(merchantId);
        TongdunApiConfig tongdunApiConfig = new TongdunApiConfig();
        tongdunApiConfig.setPartnerApp(StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunParameter.PARTNER_APP.getKey(), map, "ckd_web"));
        tongdunApiConfig.setPartnerCode(StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunParameter.PARTNER_CODE.getKey(), map, "ghwl"));
        tongdunApiConfig.setPartnerKey(StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunParameter.PARTNER_KEY.getKey(), map, "ae28ff1a3e424235ad14e40ec88b8938"));
        tongdunApiConfig.setQueryUrl(StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunParameter.QUERY_URL.getKey(), map, "https://apitest.tongdun.cn/preloan/report/v9"));
        tongdunApiConfig.setSubmitUrl(StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunParameter.SUBMIT_URL.getKey(), map, "https://apitest.tongdun.cn/preloan/apply/v5"));
        return tongdunApiConfig;
    }

    /**
     * 得到同盾信用报告风控规则id
     *
     * @param merchantId 商家id
     * @return
     */
    public TongdunRiskRuleIdConfig getTongdunRiskRuleIdConfig(Integer merchantId) {
        Map<String, String> map = this.getMerchantConfigParameters(merchantId);
        TongdunRiskRuleIdConfig ruleIdConfig = new TongdunRiskRuleIdConfig();
        String ruleIds = StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunRuleIdParameter.COURT_LOSEFAITH.getKey(), map, "2695083");
        ruleIdConfig.setCourtLoseFaithRuleIds(this.convertToList(ruleIds));
        ruleIds = StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunRuleIdParameter.COURT_EXECUTION.getKey(), map, "2695087");
        ruleIdConfig.setCourtExecutionRuleIds(this.convertToList(ruleIds));
        ruleIds = StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunRuleIdParameter.COURT_CASERULEIDS.getKey(), map, "2695097");
        ruleIdConfig.setCourtCaseRuleIds(this.convertToList(ruleIds));
        ruleIds = StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunRuleIdParameter.DISCREDIT.getKey(), map, "2695133,2695175");
        ruleIdConfig.setDiscreditRuleIds(this.convertToList(ruleIds));
        ruleIds = StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunRuleIdParameter.FUZZYNAMEHITS.getKey(), map, "2695105,2695101,2695103,2695099");
        ruleIdConfig.setFuzzyNameHitsRuleIds(this.convertToList(ruleIds));
        ruleIds = StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunRuleIdParameter.CRIMINALWANTED.getKey(), map, "2696223");
        ruleIdConfig.setCriminalWantedRuleIds(this.convertToList(ruleIds));
        ruleIds = StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunRuleIdParameter.PHONEN_UMBER_RISKS.getKey(), map, "2695125,2695127,2695129");
        ruleIdConfig.setPhoneNumberRisksRuleIds(this.convertToList(ruleIds));
        ruleIds = StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunRuleIdParameter.PLATFORM_APPLY7.getKey(), map, "2695233,2695271");
        ruleIdConfig.setPlatformApply7RuleIds(this.convertToList(ruleIds));
        ruleIds = StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunRuleIdParameter.PLATFORM_APPLY30.getKey(), map, "2695273");
        ruleIdConfig.setPlatformApply30RuleIds(this.convertToList(ruleIds));
        ruleIds = StringUtilsExt.safeGetMapValue(ConfigParameter.TongdunRuleIdParameter.PLATFORM_APPLY90.getKey(), map, "2695273");
        ruleIdConfig.setPlatformApply90RuleIds(this.convertToList(ruleIds));
        return ruleIdConfig;
    }

    /**
     * 获取通用配置参数
     * @param merchantId
     * @return
     */
    public GeneralConfig getGeneralConfig(Integer merchantId) {
        Map<String, String> map = this.getMerchantConfigParameters(merchantId);
        GeneralConfig generalConfig = new GeneralConfig();
        generalConfig.setYingyongbaoAddr(StringUtilsExt.safeGetMapValue(ConfigParameter.GeneralParameter.YINGYONGBAO.getKey(), map, ""));
        return generalConfig;
    }

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