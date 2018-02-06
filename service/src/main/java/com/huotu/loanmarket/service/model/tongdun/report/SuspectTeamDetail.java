package com.huotu.loanmarket.service.model.tongdun.report;

import lombok.Getter;
import lombok.Setter;

/**
 * 复杂网络风险详情
 * @author wm
 */
@Setter
@Getter
public class SuspectTeamDetail {
    /**
     * 匹配字段值
     * 形如：dim_value:"510723198802060000" 其中510723198802060000是身份证
     */
    private String  dim_value;
    /**
     * 风险群体编号
     * 显示命中的风险群体编号
     */
    private String  group_id;

    private Integer total_cnt;
}