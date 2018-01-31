/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.model.project;

import lombok.Data;


/**
 * @author guomw
 * @date 31/01/2018
 */
@Data
public class ProjectVo {

    private Integer loanId;
    /**
     * 名称
     */
    private String name;
    /**
     * 标签，英文逗号分割
     */
    private String tag;

    /**
     * 最高可贷款金额
     */
    private double maxMoney;
    /**
     * 最小可贷款金额
     */
    private double minMoney;

    /**
     * 贷款期限单位（默认只有天和月）
     * 0-天
     * 1-月
     * 2-年
     */
    private int deadlineUnit;
    /**
     * 利率，对应单位
     */
    private double interestRate;

    /**
     * logo
     */
    private String logo;

    /**
     * 是否热门
     */
    private int isHot;
    /**
     * 是否最新
     */
    private int isNew;

    /**
     * 申请类型
     */
    private int applyType;
    /**
     * 申请连接
     */
    private String applyUrl;
}
