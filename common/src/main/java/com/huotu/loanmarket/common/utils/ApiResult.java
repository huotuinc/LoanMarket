/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.common.utils;

import com.huotu.loanmarket.common.enums.ICommonEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * App期望获得的api响应
 *
 * @author CJ
 */
@Getter
@Setter
public class ApiResult<T> {

    /**
     * 逻辑状态返回 ：1成功,0 失败.
     */
    private T resultCode;
    /**
     * 逻辑状态描述
     */
    private String resultMsg;

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 数据
     */
    private Object data;


    public static ApiResult resultWith(int code, String message) {
        ApiResult result =new ApiResult();
        result.setResultCode(code);
        result.setResultMsg(message);
        return result;
    }
    public static ApiResult resultWith(int code, String message, Object data) {
        ApiResult result =new ApiResult();
        result.setData(data);
        result.setResultCode(code);
        result.setResultMsg(message);
        return result;
    }


    public static <T extends ICommonEnum> ApiResult resultWith(T code) {
        return resultWith(code, "",null);
    }

    public static <T extends ICommonEnum> ApiResult resultWith(T code, String description,Object objData)  {
        ApiResult result = new ApiResult();
        result.setResultCode(code.getCode());
        if(StringUtils.isEmpty(description)) {
            result.setResultMsg(code.getName());
        } else {
            result.setResultMsg(description);
        }
        result.setData(objData);
        return result;
    }

    public static <T extends ICommonEnum> ApiResult resultWith(T code,String description) {

        return resultWith(code, description,null);
    }
    public static <T extends ICommonEnum> ApiResult resultWith(T code,Object objData) {

        return resultWith(code, null,objData);
    }





}
