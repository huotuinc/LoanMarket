package com.huotu.loanmarket.service.model.tongdun;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 信用报告-风控信息(同盾)
 * @author wm
 */
@Getter
@Setter
public class ApiRiskControlVo {
    /**
     * 综合评分
     */
    private Integer finalScore;
    /**
     * 平台建议
     */
    private String finalDecision;
    /**
     * 法院失信
     */
    private Integer courtLoseFaith;
    /**
     * 法院执行
     */
    private Integer courtExecution;
    /**
     * 法院结案
     */
    private Integer courtCase;
    /**
     * 信贷逾期
     */
    private Integer discredit;
    /**
     * 模糊名单命中
     */
    private List<String> fuzzyNameHits;
    /**
     * 犯罪通缉
     */
    private Integer criminalWanted;
    /**
     * 手机号风险
     */
    private List<String> phoneNumberRisks;
    /**
     * 7天内申请的小贷平台
     */
    private Integer platformApply7;
    /**
     * 30天内申请的小贷平台
     */
    private Integer platformApply30;
    /**
     * 3个月内申请的小贷平台
     */
    private Integer platformApply90;
}