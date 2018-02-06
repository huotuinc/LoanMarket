package com.huotu.loanmarket.service.model.tongdun.report;

import lombok.Getter;
import lombok.Setter;

/**
 * 法院详情
 * @author wm
 */
@Getter
@Setter
public class CourtDetail {
    /**
     * 风险类型
     */
    private String fraud_type;
    /**
     * 被执行人姓名
     */
    private String name;
    /**
     * 年龄
     */
    private String age;
    /**
     *性别
     */
    private String gender;
    /**
     *省份
     */
    private String province;
    /**
     *立案时间
     */
    private String  filling_time;
    /**
     *执行法院
     */
    private String court_name;
    /**
     *做出执行依据单位
     */
    private String execution_department;
    /**
     *生效法律文书确定的义务
     */
    private String duty;
    /**
     *被执行人的履行情况
     */
    private String  situation;
    /**
     *失信被执行人行为具体情形
     */
    private String  discredit_detail;
    /**
     *执行依据文号
     */
    private String  execution_base;
    /**
     *案号
     */
    private String  case_number;
    /**
     *执行标的
     */
    private String  execution_number;
    /**
     *执行状态
     */
    private String  execution_status;
}