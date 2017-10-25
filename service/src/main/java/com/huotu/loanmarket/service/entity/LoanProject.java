package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author allan
 * @date 23/10/2017
 */
@Setter
@Getter
@Table(name = "Loan_Project")
@Entity
public class LoanProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    /**
     * 名称
     */
    @Column(name = "Name")
    private String name;
    /**
     * 标签，英文逗号分割
     */
    @Column(name = "Tag")
    private String tag;
    /**
     * 可贷金额，英文逗号分开
     */
    @Column(name = "Enable_Money")
    private String enableMoney;
    /**
     * 贷款期限，英文逗号分割
     */
    @Column(name = "Deadline")
    private String deadline;
    /**
     * 贷款期限单位
     * 0-天
     * 1-月
     * 2-年
     */
    @Column(name = "Deadline_Unit")
    private int deadlineUnit;
    /**
     * 利率，对应单位
     */
    @Column(name = "Interest_Rate")
    private double interestRate;
    /**
     * 最快放款时间，单位：天
     */
    @Column(name = "Fastest_GetTime")
    private int fastestGetTime;
    /**
     * 申请材料，英文逗号分割
     */
    @Column(name = "Application_Material")
    private String ApplicationMaterial;
    /**
     * 创建时间
     */
    @Column(name = "Create_Time")
    private Date createTime;
    /**
     * logo
     */
    @Column(name = "Logo")
    private String logo;
    /**
     * 描述
     */
    @Column(name = "Desc")
    private String desc;
    /**
     * 是否删除
     */
    @Column(name = "IsDelete")
    private int isDelete;
    /**
     * 应用类型
     */
    @Column(name = "Apply_Type")
    private int applyType;
    /**
     * 是否热门
     */
    @Column(name = "Is_Hot")
    private int isHot;
    /**
     * 是否最新
     */
    @Column(name = "Is_New")
    private int isNew;
    /**
     * 头部排序字段
     */
    @Column(name = "Top_Sort_Num")
    private int topSortNum;
    /**
     * 联系人
     */
    @Column(name = "Contacter")
    private String contacter;
    /**
     * 电话
     */
    @Column(name = "Phone")
    private String phone;

}
