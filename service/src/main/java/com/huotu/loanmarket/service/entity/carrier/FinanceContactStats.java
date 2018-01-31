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
 * 金融联系人
 * @author luyuanyuan on 2018/1/11.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_finance_contact_stats")
public class FinanceContactStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** order_id */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 机构分类
     */
    @Column(name = "contact_type")
    private String contactType;

    /**
     * 近6月通话号码数量
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
