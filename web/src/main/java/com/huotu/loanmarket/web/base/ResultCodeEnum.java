package com.huotu.loanmarket.web.base;

/**
 * @author allan
 * @date 23/10/2017
 */
public enum ResultCodeEnum {
    SUCCESS(2000, "请求成功"),
    SYSTEM_BAD_REQUEST(5000, "系统请求失败");

    private int resultCode;
    private String resultMsg;

    ResultCodeEnum(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
