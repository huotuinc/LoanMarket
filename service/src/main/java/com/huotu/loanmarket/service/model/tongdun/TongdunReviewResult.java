package com.huotu.loanmarket.service.model.tongdun;

import lombok.Getter;
import lombok.Setter;

/**
 * 同盾审查执行结果汇总信息，该信息将入库保存
 * @author wm
 */
@Getter
@Setter
public class TongdunReviewResult {
    /**
     * 对应requestlog的自定id
     */
    private Long Id;
    /**
     * 申请返回的结果
     */
    private PreLoanSubmitResponse submitResponse;
    /**
     * 报告返回的结果
     */
    private PreLoanQueryResponse queryResponse;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商户id
     */
    private Long merchantId;
    /**
     * 执行状态
     */
    private Integer state;
    /**
     * 错误信息
     */
    private String errorDesc;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 请求的参数
     */
    private String requestPrams;
}