package com.huotu.loanmarket.service.entity.project;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 产品申请记录表
 *
 * @author hxh
 * @date 2017-10-26
 */
@Getter
@Setter
@Entity
@Table(name = "zx_project_apply_log")
public class ProjectApplyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    /**
     * 用户编号
     */
    @Column(name = "User_Id")
    private Integer userId;
    /**
     * 产品编号
     */
    @Column(name = "Project_Id")
    private int projectId;

    @Column(name = "Apply_Time")
    private Date applyTime;
}
