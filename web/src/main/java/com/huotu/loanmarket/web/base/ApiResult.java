package com.huotu.loanmarket.web.base;

import lombok.Data;

/**
 * @author allan
 * @date 23/10/2017
 */
@Data
public class ApiResult<T> {
    private int resultCode;
    private String resultMsg;
    private T data;

    public static ApiResult resultWith(ResultCodeEnum resultCode, Object data) {
        ApiResult apiResult = new ApiResult();
        apiResult.resultCode = resultCode.getResultCode();
        apiResult.resultMsg = resultCode.getResultMsg();
        apiResult.data = data;
        return apiResult;
    }

    public static ApiResult resultWith(ResultCodeEnum resultCode) {
        ApiResult apiResult = new ApiResult();
        apiResult.resultCode = resultCode.getResultCode();
        apiResult.resultMsg = resultCode.getResultMsg();
        return apiResult;
    }

    public static ApiResult resultWith(ResultCodeEnum resultCode, String msg, Object data) {
        ApiResult apiResult = new ApiResult();
        apiResult.resultCode = resultCode.getResultCode();
        apiResult.resultMsg = msg;
        apiResult.data = data;
        return apiResult;
    }
}
