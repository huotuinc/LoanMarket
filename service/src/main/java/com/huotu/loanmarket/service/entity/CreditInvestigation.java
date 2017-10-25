package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by hxh on 2017-10-23.
 */
@Getter
@Setter
@Entity
@Table(name = "Credit_Investigation")
public class CreditInvestigation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;
    /**
     * 用户编号
     */
    @OneToOne(mappedBy = "Loan_User")
    private LoanUser loanUser;
}
