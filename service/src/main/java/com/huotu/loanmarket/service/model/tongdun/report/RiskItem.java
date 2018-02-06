package com.huotu.loanmarket.service.model.tongdun.report;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 风险信息
 * @author wm
 */
@Getter
@Setter
public class RiskItem {
    /**
     * 检查项编号
     */
    private Long item_id;
    /**
     * 检查项名称
     */
    private String item_name;
    /**
     * 风险级别，low,medium,high
     */
    private String risk_level;
    /**
     * 检查项分组
     */
    private String group;
    /**
     * 风险详情
     */
    private RiskItemDetail item_detail;
    /**
     * 风险类型
     */
    private String type;
}