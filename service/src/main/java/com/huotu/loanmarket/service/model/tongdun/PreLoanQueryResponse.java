package com.huotu.loanmarket.service.model.tongdun;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huotu.loanmarket.service.model.tongdun.report.AddressDetect;
import com.huotu.loanmarket.service.model.tongdun.report.RiskItem;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 贷前风险报告返回实体
 *
 * @author wm
 */
@Getter
@Setter
public class PreLoanQueryResponse implements Serializable {
    private static final long serialVersionUID = 4152462211121573434L;

    /**
     * 接口调用是否成功
     */
    private Boolean success = false;
    /**
     * 扫描出来的风险项
     */
    private List<RiskItem> risk_items;
    /**
     * 归属地解析
     */
    private AddressDetect address_detect;
    /**
     * 申请编号
     */
    private String application_id;
    /**
     * 风险结果
     */
    private String final_decision;
    /**
     * 报告编号
     */
    private String report_id;
    /**
     * 扫描时间
     */
    private Long apply_time;
    /**
     * 报告时间
     */
    private Long report_time;
    /**
     * 风险分数
     */
    private Integer final_score;
}