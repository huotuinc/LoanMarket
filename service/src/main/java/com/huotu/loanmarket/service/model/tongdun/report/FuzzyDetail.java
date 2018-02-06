package com.huotu.loanmarket.service.model.tongdun.report;

import lombok.Getter;
import lombok.Setter;

/**
 * 模糊名单命中详情
 * @author wm
 */
@Getter
@Setter
public class FuzzyDetail {
    /**
     * 风险类型，子规则命中风险类型，值为中文展示名
     */
    private String fraud_type;
    /**
     * 模糊身份证
     */
    private String fuzzy_id_number;
    /**
     * 姓名
     */
    private String fuzzy_name;
}