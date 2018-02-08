package com.huotu.loanmarket.service.entity.project;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 产品浏览记录表
 *
 * @author hxh
 * @date 2017-10-26
 */
@Getter
@Setter
@Entity
@Table(name = "zx_project_view_log")
public class ProjectViewLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "User_Id")
    private int userId;
    @Column(name = "Project_Id")
    private int projectId;
    @Column(name = "View_Time",columnDefinition = "datetime")
    private LocalDateTime viewTime;
}
