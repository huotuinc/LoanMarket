package com.huotu.loanmarket.service.model.tongdun;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 同盾常用的风险规则id配置
 * 主要用于前台信用报告中输出指定规则的信息
 * @author wm
 */
@Getter
@Setter
public class TongdunRiskRuleIdConfig {
    /**
     * 法院失信的规则id
     */
    private List<Integer> courtLoseFaithRuleIds;
    /**
     * 法院执行的规则id
     */
    private List<Integer> courtExecutionRuleIds;
    /**
     * 法院结案的规则id
     */
    private List<Integer> courtCaseRuleIds;
    /**
     * 信贷逾期的规则id
     */
    private List<Integer> discreditRuleIds;
    /**
     * 模糊名单命中的规则id
     */
    private List<Integer> fuzzyNameHitsRuleIds;
    /**
     * 犯罪通缉的规则id
     */
    private List<Integer> criminalWantedRuleIds;
    /**
     * 手机号风险的规则id
     */
    private List<Integer> phoneNumberRisksRuleIds;
    /**
     * 7天小贷申请的规则id
     */
    private List<Integer> platformApply7RuleIds;

    /**
     * 30天小贷申请的规则id
     */
    private List<Integer> platformApply30RuleIds;

    /**
     * 3个月小贷申请的规则id
     */
    private List<Integer> platformApply90RuleIds;
}