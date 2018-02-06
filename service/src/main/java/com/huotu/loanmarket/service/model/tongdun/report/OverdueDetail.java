package com.huotu.loanmarket.service.model.tongdun.report;

import lombok.Getter;
import lombok.Setter;

/**
 * 逾期详情
 * @author wm
 */
@Getter
@Setter
public class OverdueDetail {
    /**
     * 逾期金额
     * (0, 1000],(1000, 5000],(5000, 10000],(10000, 50000],(50000, 100000],(100000, 500000],500000+
     */
    private String overdue_amount_range;
    /**
     * 逾期笔数
     */
    private Integer overdue_count;
    /**
     * 逾期天数
     * (0, 30],(30, 60],(60, 90],(90, 180],(180, 270],(270, 360],360+
     */
    private String overdue_day_range;
    /**
     * 逾期入库时间
     * 年月,例如：2016-09
     */
    private String overdue_time;
}