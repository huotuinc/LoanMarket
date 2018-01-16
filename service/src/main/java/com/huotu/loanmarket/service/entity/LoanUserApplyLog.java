package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author hxh
 * @date 2017-10-26
 */
@Getter
@Setter
@Entity
@Table(name = "Loan_User_Apply_Log")
public class LoanUserApplyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    /**
     * 用户编号
     */
    @Column(name = "User_Id")
    private Long userId;
    /**
     * 产品编号
     */
    @Column(name = "Project_Id")
    private int projectId;

    @Column(name = "Apply_Time")
    private Date applyTime;
}
