package com.huotu.loanmarket.service.model.tongdun.report;

import com.alibaba.fastjson.JSONArray;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 风向描述详情
 * 多种类型实体汇总
 * @author wm
 */
@Getter
@Setter
public class RiskItemDetail {
    /**
     * 信贷逾期名单，规则对应discredit_count
     */
    private Integer discredit_times;
    /**
     * 逾期详情，规则对应discredit_count
     */
    private List<OverdueDetail> overdue_details;
    /**
     * 多头借贷，规则对应platform_detail
     */
    private Integer platform_count;
    /**
     * 借贷详情，规则对应platform_detail
     */
    private List<String> platform_detail;
    /**
     * 多平台细分维度详情，规则对应platform_detail
     */
    private List<PlatformDetailDimension> platform_detail_dimension;
    /**
     * 高风险区域，规则对应custom_list
     */
    private List<String>  high_risk_areas;
    /**
     *列表数据，规则对应custom_list
     */
    private List<String> hit_list_datas;
    /**
     * 风险类型
     */
    private String  fraud_type;
    /**
     * 频度详情，对应规则frequency_detail
     */
    private List<FrequencyDetail> frequency_detail_list;
    /**
     * 命中名单详情列表，对应规则black_list, grey_list, fuzzy_list
     */
    private List<NameListHitDetail> namelist_hit_details;
    /**
     * 规则的类型
     */
    @Deprecated
    private String type = "";
    /**
     * 复杂网络风险详情，对应规则suspect_team_detail
     */
    private JSONArray suspect_team_detail_list;
}