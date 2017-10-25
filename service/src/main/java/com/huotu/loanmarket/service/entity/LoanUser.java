package com.huotu.loanmarket.service.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hxh on 2017-10-23.
 */
@Entity
@Table(name = "Load_User")
public class LoanUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    /**
     *用户名
     */
    @Column(name = "Account")
    private int account;
    /**
     * 真实姓名
     */
    @Column(name = "RealName")
    private String realName;

    /**
     * 昵称
     */
    @Column(name = "NickName")
    private String nickName;
    /**
     * 身份证号
     */
    @Column(name = "IdCard")
    private String idCard;
    /**
     * 创建时间
     */
    @Column(name = "Create_Time")
    private Date createTime;
    /**
     * 是否冻结
     */
    @Column(name = "IsFreeze")
    private int isFreeze;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Loan_User_Id")
    private CreditInvestigation creditInvestigation;
}
