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
public class UserInfoVo {

    /**
     * 用户ID
     */
    private  long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户token
     */

    private String userToken;
    /**
     * 头像
     */
    private  String headimg;
    /**
     * 认证状态
     */
    private int authStatus;

    /**
     * 信用估值(价值)
     */
    private int creditValue;
}
