package com.huotu.loanmarket.service.enums;

import com.huotu.loanmarket.common.enums.ICommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author hxh
 * @Date 2018/1/30 16:10
 */
@Getter
@AllArgsConstructor
public enum SesameResultCode implements ICommonEnum {
    /**
     * 枚举
     */
    USERID_EMPTY(4100, "用户编号未传"),
    USER_EMPTY(4101, "用户不存在");
    private int code;

    private String name;
}
