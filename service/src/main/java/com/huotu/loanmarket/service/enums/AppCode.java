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
 *
 * @author hot
 * @date 2017/11/1
 */
@Getter
@AllArgsConstructor
public enum AppCode implements ICommonEnum {
    /**
     * 枚举
     */
    SUCCESS(2000, "操作成功"),
    SYSTEM_BAD_REQUEST(5000, "系统请求失败"),
    SIGN_NOT_PASS(4001, "签名参数未传"),
    SIGN_ERROR(4002, "签名错误"),
    /**
     * token失效
     */
    TOKEN_ERROR(4003, "你的账号已在另一台设备登录。如非本人操作，则密码可能已泄露，建议修改密码。"),
    TIMESTAMP_ERROR(4004, "timestamp失效"),
    NOT_DATA(4005,"暂无数据"),
    ERROR(4006,"操作失败"),
    NOT_UPDATE(4007,"当前已是最新版本"),
    PARAMETER_ERROR(4008, "参数错误"),
    ;

    private int code;

    private String name;
}
