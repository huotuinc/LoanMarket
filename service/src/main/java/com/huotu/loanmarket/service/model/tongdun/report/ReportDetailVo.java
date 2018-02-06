package com.huotu.loanmarket.service.model.tongdun.report;

import com.huotu.loanmarket.service.model.tongdun.PreLoanSubmitRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 报告vo
 * @author wm
 */
@Getter
@Setter
public class ReportDetailVo {
    /**
     * 报告id
     */
    private String reportId;
    /**
     * 申请id
     */
    private String applyId;
    /**
     * 报告时间
     */
    private String reportTime;
    /**
     * 得分
     */
    private Integer finalScore;
    /**
     * 结果
     */
    private String finalDecision;
    /**
     * 风险数
     */
    private Integer riskCount;
    /**
     * 提交的信息
     */
    private PreLoanSubmitRequest submitRequest;
    /**
     * 归属地信息
     */
    private AddressDetect addressDetect;
    /**
     * 分组过的风险信息
     */
    private Map<String,List<RiskItem>> riskItems;
}