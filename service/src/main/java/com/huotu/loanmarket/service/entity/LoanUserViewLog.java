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
@Table(name = "Loan_User_View_Log")
public class LoanUserViewLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "User_Id")
    private int userId;
    @Column(name = "Project_Id")
    private int projectId;
    @Column(name = "View_Time")
    private Date viewTime;
}
