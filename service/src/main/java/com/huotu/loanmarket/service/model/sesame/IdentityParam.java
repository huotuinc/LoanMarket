package com.huotu.loanmarket.service.model.sesame;

import com.huotu.loanmarket.service.config.SesameSysConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author hxh
 * @Date 2018/1/31 11:25
 */
@Getter
@Setter
public class IdentityParam {
    /**
     * 身份证号码
     */
    private String certNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 认证类型
     */
    private String certType = SesameSysConfig.AUTHENTICATION_TYPE;

    @Override
    public String toString() {
        return '{' +
                "\"certNo\":\"" + certNo + '\"' +
                ", \"name\":\"" + name + '\"' +
                ", \"certType\":\"" + certType + '\"' +
                '}';
    }
}
