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
 * 收货地址
 * @author luyuanyuan on 2018/2/1.
 */
@Getter
@Setter
@Entity
@Table(name = "zx_receiver")
public class Receiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** order_id */
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "name")
    private String name;

    @Column(name = "area")
    private String area;

    @Column(name = "address")
    private String address;

    @Column(name = "mobile")
    private String mobile;
}
