/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.exceptions;

import com.huotu.loanmarket.common.enums.ICommonEnum;
import com.huotu.loanmarket.service.enums.AppCode;

/**
 * 错误信息处理,返回错误信息
 * 专门用来接口业务判断条件错误信息
 * @author guomw
 * @date 17/11/2017
 */
public class ErrorMessageException extends RuntimeException {
    public int code;

    /**
     * 枚举异常信息
     * @param commonEnum
     */
    public ErrorMessageException(ICommonEnum commonEnum){
        super(commonEnum.getName());
        code=commonEnum.getCode();
    }

    /**
     * 传入指定code和message
     * @param errorCode
     * @param message
     */
    public ErrorMessageException(int errorCode, String message){
        super(message);
        code=errorCode;
    }

    /**
     * 错误信息，code：5000
     * @param message
     */
    public ErrorMessageException(String message){
        super(message);
        code= AppCode.SYSTEM_BAD_REQUEST.getCode();
    }

}
