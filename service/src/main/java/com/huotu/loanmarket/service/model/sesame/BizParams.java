package com.huotu.loanmarket.service.model.sesame;

import com.huotu.loanmarket.service.config.SesameSysConfig;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author hxh
 * @date 2017-11-29
 */
@Getter
@Setter
public class BizParams {
    /**
     * 默认H5方式
     */
    private String auth_code = SesameSysConfig.SESAME_CREDIT_TYPE ;
    /**
     * 默认商户app接入
     */
    private String channelType = SesameSysConfig.SESAME_CREDIT_ACCESS_TYPE;
    /**
     * 商户自定义数据
     */
    private String state;

    @Override
    public String toString() {
        return "{" +
                "\"auth_code\":\"" + auth_code + '\"' +
                ", \"channelType\":\"" + channelType + '\"' +
                ", \"state\":\"" + state + '\"' +
                '}';
    }
}
