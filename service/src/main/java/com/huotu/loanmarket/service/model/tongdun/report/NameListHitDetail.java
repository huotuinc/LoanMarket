package com.huotu.loanmarket.service.model.tongdun.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 命中名单详情
 * 关注名单，风险名单，模糊名单
 * @author wm
 */
@Getter
@Setter
public class NameListHitDetail {
    /**
     * 描述
     */
    private String description;
    /**
     * 风险类型
     */
    private String fraud_type;
    /**
     * 匹配类型
     */
    private String hit_type_displayname;
    /**
     * 规则标识
     */
    private String type;
    /**
     * 法院详情，风险名单专有属性
     */
    private List<CourtDetail> court_details;
    /**
     * 模糊名单命中详情，模糊名单专有
     */
    private List<FuzzyDetail> fuzzy_detail_hits;
}