package com.huotu.loanmarket.service.entity.carrier;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 每月消费统计
 * @author luyuanyuan on 2018/1/11.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_consumption_stats_per_month")
public class ConsumptionStatsPerMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** order_id */
    @Column(name = "order_id")
    private Long orderId;
    /**
     * 月份
     */
    @Column(name = "month")
    private String month;

    /**
     * 月消费金额
     */
    @Column(name = "consume_amount")
    private String consumeAmount;

    /**
     * 月充值金额
     */
    @Column(name = "recharge_amount")
    private String rechargeAmount;

}
