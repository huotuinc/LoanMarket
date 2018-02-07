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
import com.huotu.loanmarket.common.utils.MaskUtils;
import com.huotu.loanmarket.common.utils.RegexUtils;
import com.huotu.loanmarket.service.entity.system.AppSystemVersion;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.DeviceTypeEnum;
import com.huotu.loanmarket.service.enums.PackageTypeEnum;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.model.system.ShareInfoVo;
import com.huotu.loanmarket.service.model.user.UserInfoVo;
import com.huotu.loanmarket.service.service.BaseService;
import com.huotu.loanmarket.service.service.system.AppVersionService;
import com.huotu.loanmarket.service.service.system.SmsTemplateService;
import com.huotu.loanmarket.service.service.system.SystemService;
import com.huotu.loanmarket.service.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private static final Log log = LogFactory.getLog(SystemController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private AppVersionService appVersionService;
    @Autowired
    private UserService userService;
    @Autowired
    private SmsTemplateService smsTemplateService;
    @Autowired
    private BaseService baseService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult test() {
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    /**
     * 初始化接口
     *
     * @param userId
     * @param userToken
     * @param appVersion
     * @param osType
     * @return
     */
    @RequestMapping("/init")
    @ResponseBody
    public ApiResult init(@RequestHeader(value = Constant.APP_USER_ID_KEY, required = false, defaultValue = "0") Long userId,
                          @RequestHeader(value = Constant.APP_USER_TOKEN_KEY, required = false, defaultValue = "") String userToken,
                          @RequestHeader(value = Constant.APP_VERSION_KEY, defaultValue = "1.0") String appVersion,
                          @RequestHeader(value = Constant.APP_SYSTEM_TYPE_KEY, defaultValue = "android") String osType
    ) throws URISyntaxException {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        PackageTypeEnum packageTypeEnum = PackageTypeEnum.Simple;

        DeviceTypeEnum deviceTypeEnum = DeviceTypeEnum.Android;
        if (DeviceTypeEnum.Android.getName().equalsIgnoreCase(osType)) {
            deviceTypeEnum = DeviceTypeEnum.Android;
        } else if (DeviceTypeEnum.IOS.getName().equalsIgnoreCase(osType)) {
            deviceTypeEnum = DeviceTypeEnum.IOS;
        }

        if (!deviceTypeEnum.equals(DeviceTypeEnum.H5)) {
            AppSystemVersion appSystemVersion = appVersionService.findByAppVersion(deviceTypeEnum, appVersion);
            if (appSystemVersion != null) {
                packageTypeEnum = appSystemVersion.getPackageType();
            }
        }
        //包类型
        map.put("packageType", packageTypeEnum.getCode());
        if (userService.checkLoginToken(Constant.MERCHANT_ID, userId, userToken)) {
            try {
                User user = userService.findByMerchantIdAndUserId(Constant.MERCHANT_ID, userId);
                if (user != null) {
                    UserInfoVo userInfoVo = new UserInfoVo();
                    userInfoVo.setUserId(user.getUserId());
                    userInfoVo.setUserName(user.getUserName());
                    userInfoVo.setUserToken(user.getUserToken());
                    userInfoVo.setHeadimg(user.getHeadimg());
                    userInfoVo.setAuthStatus(user.getAuthStatus().getCode());
                    userService.updateLastLoginTime(userId);
                    map.put("userInfo", userInfoVo);
                }
            } catch (Exception e) {
                log.error(e);
            }
        }


        map.put("aboutUrl", "http://wwww.baidu.com");
        map.put("regAgreementUrl", "http://wwww.baidu.com");
        map.put("creditAuthUrl", "http://wwww.baidu.com");

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
        } else if (DeviceTypeEnum.IOS.getName().equalsIgnoreCase(osType)) {
            deviceTypeEnum = DeviceTypeEnum.IOS;
        } else {
            deviceTypeEnum = DeviceTypeEnum.Android;
        }

        AppSystemVersion version = systemService.checkUpdate(appVersion, deviceTypeEnum);
        if (version != null) {
            map.put("versionInfo", version);
            return ApiResult.resultWith(AppCode.SUCCESS, map);
        } else {
            return ApiResult.resultWith(AppCode.NOT_UPDATE);
        }

    }


    /**
     * 发送验证码接口
     *
     * @param mobile   手机号
     * @param safeCode 安全码
     * @return
     */
    @RequestMapping("/sendVerifyCode")
    @ResponseBody
    public ApiResult sendVerifyCode(String mobile,
                                    @RequestParam(value = "safeCode", required = false) String safeCode

    ) {
        if (!RegexUtils.checkMobile(mobile)) {
            return ApiResult.resultWith(UserResultCode.CODE1);
        }
        try {

            if (smsTemplateService.sendVerifyCode(Constant.MERCHANT_ID, mobile, safeCode)) {
                return ApiResult.resultWith(UserResultCode.CODE10);
            }
        } catch (ErrorMessageException e) {
            return ApiResult.resultWith(e.code, e.getMessage());
        }
        return ApiResult.resultWith(UserResultCode.CODE8);
    }

    /**
     * 获取分享接口
     * @param userId 用户id
     * @return 结果
     */
    @RequestMapping("/getInviteShareConfig")
    @ResponseBody
    public ApiResult getInviteShareConfig(@RequestHeader(value = Constant.APP_USER_ID_KEY, required = false, defaultValue = "0") Long userId) {
        String tempTitle = "{name}邀请您认证个人信息，共享征信数据！", tempDescription = "我刚在过海征信App里查看了非常详细的征信报告，你也快来试试吧。";
        User user = userService.findByMerchantIdAndUserId(Constant.MERCHANT_ID, userId);
        if (user != null) {
            ShareInfoVo shareInfo = ShareInfoVo.builder()
                    .title(tempTitle.replace("{name}", MaskUtils.maskMobile(user.getUserName())) )
                    .description(tempDescription)
                    .icon("http://cdn1.51morecash.com/logozx.png")
                    .url(baseService.h5HomeURI() + "invite?i=" + userId).build();
            return ApiResult.resultWith(AppCode.SUCCESS, shareInfo);
        }
        return ApiResult.resultWith(AppCode.ERROR, "没有找到用户");
    }
}
