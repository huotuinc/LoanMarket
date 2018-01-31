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
 * 静默活跃统计
 * @author luyuanyuan on 2018/1/12.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_active_silence_stats")
public class ActiveSilenceStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** order_id */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 近3月通话活跃天数
     */
    @Column(name = "active_day_call_three")
    private int activeDayCallThree;

    /**
     * 近3月无通话静默天数
     */
    @Column(name = "silence_day_call_three")
    private int silenceDayCallThree;

    /**
     * 近3月连续无通话静默>=3天的次数
     */
    @Column(name = "continue_silence_day_over_three")
    private int continueSilenceDayOverThree;

}
