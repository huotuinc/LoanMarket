package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 类目关联表
 * Created by hxh on 2017-10-23.
 */
@Getter
@Setter
@Entity
@Table(name = "Category_Relation")
public class CategoryRelation {
    @Id
    @Column(name = "Id")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "Loan_Project_Id")
    private LoanProject loanProject;
    /**
     * 类目编号
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "Category_Id")
    private LoanCategory loanCategory;
}
