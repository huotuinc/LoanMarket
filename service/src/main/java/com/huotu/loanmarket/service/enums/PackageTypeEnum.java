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
public enum PackageTypeEnum implements ICommonEnum {

    /**
     *Simple
     */
    Simple(0, "简单"),
    /**
     * Normal
     */
    Normal(1, "正常"),;

    private int code;
    private String name;
}
