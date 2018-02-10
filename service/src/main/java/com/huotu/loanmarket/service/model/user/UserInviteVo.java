/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.model.user;

import lombok.Data;

/**
 * @author guomw
 * @date 31/01/2018
 */
@Data
public class UserInviteVo {

    /**
     * 邀请时间
     */
    private String inviteTime;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 手机号码
     */
    private String userName;
    /**
     * 名字
     */
    private String name;

    private String statusName;

    private Integer status;

    /**
     * 这个字段是后台查询时需要赋值的，api 用不到
     */
    private int orderCount;
}
