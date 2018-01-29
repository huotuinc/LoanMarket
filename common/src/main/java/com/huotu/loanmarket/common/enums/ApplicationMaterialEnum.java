package com.huotu.loanmarket.common.enums;

/**
 * @author allan
 * @date 30/10/2017
 */
public enum ApplicationMaterialEnum implements ICommonEnum {
    BASIC_INFO(0, "基本信息"),
    IDENTITY_INFO(1, "身份信息"),
    WORK_INFO(2, "工作信息"),
    OPERATOR_VERIFY(3, "运营商验证"),
    E_COMMERCE_VERIFY(4, "电商验证"),
    CREDIT_INVESTIGATION(5, "授权征信查询"),
    CONTACT_INFO(6, "联系人信息");

    private int code;
    private String name;

    ApplicationMaterialEnum(int code, String name) {
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
