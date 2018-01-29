package com.huotu.loanmarket.common.enums;

/**
 *
 * @author hxh
 * @date 2017-10-31
 */
public enum LoanTermEnum implements ICommonEnum{
    DAY(0, "天"),
    MONTH(1, "月"),
    YEAR(2, "年");

    private int code;
    private String name;

    LoanTermEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
