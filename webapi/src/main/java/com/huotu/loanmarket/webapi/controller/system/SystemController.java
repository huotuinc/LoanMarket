/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.webapi.controller.system;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.system.AppSystemVersion;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.DeviceTypeEnum;
import com.huotu.loanmarket.service.service.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;

/**
 * @author guomw
 * @date 29/01/2018
 */
@Controller
@RequestMapping(value = "/api/sys", method = RequestMethod.POST)
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ResponseBody
    public ApiResult test(){
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    /**
     * 初始化接口
     *
     * @param merchantId
     * @param userId
     * @return
     */
    @RequestMapping("/init")
    @ResponseBody
    public ApiResult init(@RequestHeader(Constant.APP_MERCHANT_ID_KEY) Integer merchantId,
                          @RequestHeader(value = Constant.APP_USER_ID_KEY, required = false) Integer userId,
                          @RequestHeader(value = Constant.APP_VERSION_KEY, defaultValue = "1.0") String appVersion,
                          @RequestHeader(value = Constant.APP_SYSTEM_TYPE_KEY, defaultValue = "android") String osType
    ) throws URISyntaxException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        return ApiResult.resultWith(AppCode.SUCCESS, map);
    }



    /**
     * 检查接口
     *
     * @param appVersion 版本号 格式1.*.*.*
     * @return
     */
    @RequestMapping(value = "/checkUpdate", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult checkUpdate(@RequestParam(required = false, defaultValue = "1.0.0") String appVersion,
                                 @RequestHeader(value = Constant.APP_SYSTEM_TYPE_KEY, required = false) String osType) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        DeviceTypeEnum deviceTypeEnum;
        if (DeviceTypeEnum.Android.getName().equalsIgnoreCase(osType)) {
            deviceTypeEnum = DeviceTypeEnum.Android;
        } else if (DeviceTypeEnum.Android.getName().equalsIgnoreCase(osType)) {
            deviceTypeEnum = DeviceTypeEnum.IOS;
        } else {
            return ApiResult.resultWith(AppCode.NOT_UPDATE);
        }

        AppSystemVersion version = systemService.checkUpdate(appVersion, deviceTypeEnum);
        if (version != null) {
            map.put("versionInfo", version);
            return ApiResult.resultWith(AppCode.SUCCESS, map);
        } else {
            return ApiResult.resultWith(AppCode.NOT_UPDATE);
        }

    }
}
