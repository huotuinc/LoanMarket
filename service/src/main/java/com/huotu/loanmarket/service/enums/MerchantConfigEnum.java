package com.huotu.loanmarket.service.enums;


import com.huotu.loanmarket.common.enums.ICommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hxh
 * @date 2017-11-29
 */
@Getter
@AllArgsConstructor
public enum MerchantConfigEnum implements ICommonEnum {
    /**
     * 第三方参数类型
     */
    SESAME(0, "芝麻信用接口");

    private int code;

    private String name;


}
