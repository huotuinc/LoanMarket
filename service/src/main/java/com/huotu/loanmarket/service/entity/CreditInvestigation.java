package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private Integer id;
    /**
     * 用户编号
     */
    @ManyToOne
    @JoinColumn(name = "Loan_User_Id")
    private LoanUser loanUser;
}
