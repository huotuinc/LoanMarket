package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by allan on 01/11/2017.
 */
@Entity
@Table(name = "Loan_Verify_Code")
@Setter
@Getter
public class LoanVerifyCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Code")
    private String code;
    @Column(name = "Mobile")
    private String mobile;
    @Column(name = "Send_Time")
    private Date sendTime;
    @Column(name = "Invalid_Time")
    private Date invalidTime;
}
