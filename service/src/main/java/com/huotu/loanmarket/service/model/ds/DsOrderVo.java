package com.huotu.loanmarket.service.model.ds;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author luyuanyuan on 2018/2/2.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DsOrderVo {

    /**
     * 消费次数
     */
    private Long count;

    /**
     * 平均消费金额
     */
    private BigDecimal avgMoney;

    /**
     * 最早消费时间
     */
    private String firstTiem;

    /**
     * 最近消费时间
     */
    private String lastTiem;
}
