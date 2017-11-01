package com.huotu.loanmarket.service.entity;

import com.huotu.loanmarket.common.ienum.LoanTermEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
     * 贷款期限单位
     * 0-天
     * 1-月
     * 2-年
     */
    @Column(name = "Deadline_Unit")
    private LoanTermEnum deadlineUnit = LoanTermEnum.DAY;
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
     * 描述
     */
    @Column(name = "Describe")
    private String desc;
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

    @Column(name = "Categories")
    private String categories;

}
