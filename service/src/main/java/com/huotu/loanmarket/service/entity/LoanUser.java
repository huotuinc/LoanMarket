package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hxh
 * @date 2017-10-23
 */
@Getter
@Setter
@Entity
@Table(name = "Loan_User")
public class LoanUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long userId;
    /**
     * 用户名
     */
    @Column(name = "Account")
    private String account;
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
}
