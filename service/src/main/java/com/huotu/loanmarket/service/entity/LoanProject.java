/*
 *   ~ 版权所有:杭州火图科技有限公司
 *   ~ 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *   ~
 *   ~ (c) Copyright Hangzhou Hot Technology Co., Ltd.
 *   ~ Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *   ~ 2017-2020. All rights reserved.
 */

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
    private Integer loanId;
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
     * 贷款期限单位（默认只有天和月）
     * 0-天
     * 1-月
     * 2-年
     */
    @Column(name = "Deadline_Unit")
    private int deadlineUnit;
    @Transient
    private String deadlineUnitDesc;
    /**
     * 利率，对应单位
     */
    @Column(name = "Interest_Rate")
    private double interestRate;
    /**
     * 最快放款时间，单位：天
     */
    @Column(name = "Fastest_GetTime")
    private double fastestGetTime;
    /**
     * 申请材料，英文逗号分割
     */
    @Column(name = "Application_Material")
    private String applicationMaterial;
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
     * 是否删除
     */
    @Column(name = "IsDelete")
    private int isDelete;
    /**
     * 申请类型
     */
    @Column(name = "Apply_Type")
    private int applyType;
    @Column(name = "Apply_Url")
    private String applyUrl;
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
    @Column(name = "Contact")
    private String contact;
    /**
     * 电话
     */
    @Column(name = "Phone")
    private String phone;
    /**
     * 最高可贷款金额
     */
    @Column(name = "Max_Money")
    private double maxMoney;
    /**
     * 最小可贷款金额
     */
    @Column(name = "Min_Money")
    private double minMoney;

    /**
     * 最大可贷款期限
     */
    @Column(name = "Max_Deadline")
    private int maxDeadline;
    /**
     * 最小可贷款期限
     */
    @Transient
    private int minDeadline;
    @Column(name = "Categories")
    private String categories;
    /**
     * 浏览量
     */
    @Transient
    private int viewCount;
    /**
     * 申请量
     */
    @Transient
    private int applyCount;

}
