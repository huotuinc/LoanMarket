/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.enums;


import com.huotu.loanmarket.common.enums.ICommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author guomw
 */
@Getter
@AllArgsConstructor
public enum UserAuthorizedStatusEnums implements ICommonEnum{

    AUTH_NOT(0, "未认证"),
    AUTH_ERROR(1,"认证失败"),
    AUTH_SUCCESS(2, "已认证"),
    AUTH_INVALID(3, "过期"),
    AUTH_ING(4, "认证中"),
    ;

    private int code;

    private String name;

}
