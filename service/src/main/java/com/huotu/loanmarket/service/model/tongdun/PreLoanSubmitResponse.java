package com.huotu.loanmarket.service.model.tongdun;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 贷前检查申请返回实体
 *
 * @author wm
 */
@Getter
@Setter
public class PreLoanSubmitResponse implements Serializable {

    private static final long serialVersionUID = 4152462611121573434L;
    /**
     * 是否调用成功
     */
    private Boolean success = false;
    /**
     * 贷前申请风险报告编号
     */
    private String report_id;
    /**
     * 调用失败时的错误码
     */
    private String reason_code;
    /**
     * 错误详情描述
     */
    private String reason_desc;

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("success", success)
                .append("report_id", report_id)
                .toString();
    }
}