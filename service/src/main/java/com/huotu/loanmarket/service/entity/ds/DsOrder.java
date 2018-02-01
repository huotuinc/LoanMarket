package com.huotu.loanmarket.service.entity.ds;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 电商订单
 * @author luyuanyuan on 2018/2/1.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_ds_order")
public class DsOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** order_id */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 电商订单id
     */
    @Column(name = "ds_order_id")
    private String dsOrderId;

    /**
     * 订单金额
     */
    @Column(name = "orderAmount")
    private String orderAmount;

    /**
     * 订单类型
     */
    @Column(name = "orderType")
    private String orderType;

    /**
     * 订单时间
     */
    @Column(name = "order_time")
    private String orderTime;

    /**
     * 订单状态
     */
    @Column(name = "order_status")
    private String orderStatus;

}
