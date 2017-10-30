package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 *
 * @author hxh
 * @date 2017-10-23
 */
@Setter
@Getter
@Entity
@Table(name = "Loan_Category")
public class LoanCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer categoryId;
    /**
     * 类目名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 父级编号
     */
    @Column(name = "Parent_Id")
    private String parentId;
    /**
     * 图标
     */
    @Column(name = "Icon")
    private String icon;
}
