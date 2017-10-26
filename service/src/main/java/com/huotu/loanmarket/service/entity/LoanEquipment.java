package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author hxh
 * @date 2017-10-26
 */
@Getter
@Setter
@Entity
@Table(name = "Loan_Equipment")
public class LoanEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**
     *app版本
     */
    @Column(name = "App_Version")
    private String appVersion;
    /**
     *手机操作系统
     */
    @Column(name = "Os_Version")
    private String osVersion;
    /**
     *操作类型
     */
    @Column(name = "Os_Type")
    private String osType;
}
