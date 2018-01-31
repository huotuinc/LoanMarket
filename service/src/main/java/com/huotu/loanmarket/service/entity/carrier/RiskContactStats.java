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
 * 风险联系人
 * @author luyuanyuan on 2018/1/11.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_risk_contact_stats")
public class RiskContactStats {

    /** id */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** order_id */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 风险类型
     */
    @Column(name = "risk_type")
    private String riskType;

    /**
     *  近6月通话号码数量
     */
    @Column(name = "contact_count_six")
    private int contactCountSix;

    /**
     * 近6月通话次数
     */
    @Column(name = "call_count_six")
    private int callCountSix;

    /**
     * 近6月通话时长
     */
    @Column(name = "call_time_six")
    private int callTimeSix;

}
