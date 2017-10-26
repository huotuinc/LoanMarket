package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "Loan_Apply_Log")
public class LoanApplyLog {
    /**
     * 用户编号
     */
    @Id
    @Column(name = "User_Id")
    private Integer userId;
    /**
     * 产品编号
     */
    @Column(name = "Project_Id")
    private int projectId;
    /**
     * 数量
     */
    @Column(name = "Num")
    private int num;
}
