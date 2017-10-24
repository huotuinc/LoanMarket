package com.huotu.loanmarket.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by hxh on 2017-10-23.
 */
@Setter
@Getter
@Entity
@Table(name = "Loan_Category")
public class LoanCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @OneToMany(mappedBy = "loanCategory",cascade = CascadeType.ALL)
    private List<CategoryRelation> categoryRelationList;
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
