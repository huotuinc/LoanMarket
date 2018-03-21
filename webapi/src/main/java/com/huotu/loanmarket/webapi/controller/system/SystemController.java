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
import com.huotu.loanmarket.service.entity.system.Advertisement;
import com.huotu.loanmarket.service.entity.system.AppSystemVersion;
import com.huotu.loanmarket.service.entity.system.CheckConfig;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.DeviceTypeEnum;
import com.huotu.loanmarket.service.enums.PackageTypeEnum;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.model.system.AdvertisementListVo;
import com.huotu.loanmarket.service.model.system.CheckConfigListVo;
import com.huotu.loanmarket.service.model.system.ShareInfoVo;
import com.huotu.loanmarket.service.model.user.UserInfoVo;
import com.huotu.loanmarket.service.repository.system.AdvertisementRepository;
import com.huotu.loanmarket.service.repository.system.CheckConfigRepository;
import com.huotu.loanmarket.service.service.BaseService;
import com.huotu.loanmarket.service.service.system.AppVersionService;
import com.huotu.loanmarket.service.service.system.SmsTemplateService;
import com.huotu.loanmarket.service.service.system.SystemService;
import com.huotu.loanmarket.service.service.upload.StaticResourceService;
import com.huotu.loanmarket.service.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
        //信用估值，默认2000
        int creditValue=2000;
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
                    userInfoVo.setCreditValue(user.getCreditValue());
                    userService.updateLastLoginTime(userId);
                    map.put("userInfo", userInfoVo);

                    //登录成功后，同步有信用户数据
                    UserInfoVo yxUserInfo=new UserInfoVo();

                    map.put("yxUserInfo",yxUserInfo);

                    creditValue=user.getCreditValue();
                }
            } catch (Exception e) {
                log.error(e);
            }
        }
        //信用估值
        map.put("creditValue",creditValue);
        map.put("aboutUrl", MessageFormat.format("{0}api/other/about", baseService.apiHomeURI()));
        map.put("regAgreementUrl", MessageFormat.format("{0}api/other/regAgreement", baseService.apiHomeURI()));
        map.put("creditAuthUrl", MessageFormat.format("{0}api/other/creditAuth", baseService.apiHomeURI()));
        map.put("loanProjectProcessUrl", MessageFormat.format("{0}api/projectView/loanProcess", baseService.apiHomeURI()));


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
     *
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
                    .title(tempTitle.replace("{name}", MaskUtils.maskMobile(user.getUserName())))
                    .description(tempDescription)
                    .icon("http://cdn1.51morecash.com/logozx.png")
                    .url(baseService.apiHomeURI() + "/api/user/reg?inviterId=" + userId).build();
            return ApiResult.resultWith(AppCode.SUCCESS, shareInfo);
        }
        return ApiResult.resultWith(AppCode.ERROR, "没有找到用户");
    }

    @Autowired
    private CheckConfigRepository checkConfigRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private StaticResourceService staticResourceService;

    /**
     * 获得检测配置
     *
     * @return
     */
    @RequestMapping("/getCheckConfig")
    @ResponseBody
    public ApiResult getCheckConfig() {
        CheckConfig checkConfig = checkConfigRepository.findOne(Constant.MERCHANT_ID);
        if (checkConfig != null) {
            List<CheckConfigListVo> list = new ArrayList<>();
            list.add(new CheckConfigListVo(1, checkConfig.getBlackListCheck()));
            list.add(new CheckConfigListVo(2, checkConfig.getOperatorCheck()));
            list.add(new CheckConfigListVo(3, checkConfig.getElectronicBusinessCheck()));

            return ApiResult.resultWith(AppCode.SUCCESS, list);
        }
        return ApiResult.resultWith(AppCode.ERROR, "检测没有配置");
    }


    /**
     * 获得广告列表
     *
     * @return
     */
    @RequestMapping("/getAdvertisementList")
    @ResponseBody
    public ApiResult getAdvertisementList() {
        List<AdvertisementListVo> list = new ArrayList<>();
        List<Advertisement> advertisements = advertisementRepository.findByMerchantId(Constant.MERCHANT_ID);
        BeanUtils.copyProperties(advertisements, list);
        if (list.size() > 0) {
            list.forEach(item -> {
                try {
                    item.setImageUrl(staticResourceService.getResource(item.getImageUrl()).toString());
                } catch (URISyntaxException e) {

                }
            });
        }


        return ApiResult.resultWith(AppCode.SUCCESS, list);
    }

}
