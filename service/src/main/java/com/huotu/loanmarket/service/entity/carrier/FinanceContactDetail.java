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
 * 金融联系人明细
 * @author luyuanyuan on 2018/1/11.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_finance_contact_detail")
public class FinanceContactDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** order_id */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 联系人号码
     */
    @Column(name = "contact_number")
    private String contactNumber;

    /**
     * 号码标签
     */
    @Column(name = "contact_name")
    private String contactName;
}
