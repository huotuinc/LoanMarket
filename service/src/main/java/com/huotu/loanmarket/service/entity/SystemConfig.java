/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统配置
 *
 * DatabaseVersion 数据库版本
 * @author guomw
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "zx_system_config")
public class SystemConfig {
    @Id
    @Column(length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String valueForCode;

    @Column(length = 100)
    private String remark;

    public SystemConfig() {
    }
}