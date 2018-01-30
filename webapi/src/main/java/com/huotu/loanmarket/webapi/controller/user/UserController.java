/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.webapi.controller.user;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.common.utils.RegexUtils;
import com.huotu.loanmarket.common.utils.RequestUtils;
import com.huotu.loanmarket.common.utils.StringUtilsExt;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author guomw
 * @date 30/01/2018
 */
@Controller
@RequestMapping(value = "/api/user", method = RequestMethod.POST)
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 根据手机号、密码和验证码注册用户
     *
     * @param username   用户名
     * @param password   密码(md5)
     * @param verifyCode 短信验证码
     * @param inviter    邀请者Id
     * @param request
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ApiResult register(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String verifyCode,
                              @RequestParam(required = false, defaultValue = "0") Long inviter,
                              HttpServletRequest request) {


        if (!RegexUtils.checkMobile(username)) {
            return ApiResult.resultWith(UserResultCode.CODE1);
        }

        if (StringUtils.isEmpty(password) || password.length() != Constant.PASS_WORD_LENGTH) {
            return ApiResult.resultWith(UserResultCode.CODE2);
        }

        if (StringUtils.isEmpty(verifyCode) || verifyCode.length() != Constant.VERIFY_CODE_LENGTH) {
            return ApiResult.resultWith(UserResultCode.CODE9);
        }

        User user = setUserHeaderInfo(request);
        user.setUserName(username);
        user.setPassword(password);
        user.setMerchantId(Constant.MERCHANT_ID);
        user.setRegTime(LocalDateTime.now());
        user.setLastLoginIp(StringUtilsExt.getClientIp(request));
        user.setChannelId(RequestUtils.getHeader(request, Constant.APP_CHANNELID_KEY, "default"));
        user.setInviterId(inviter);
        try {
            return ApiResult.resultWith(AppCode.SUCCESS, userService.register(user, verifyCode));
        } catch (ErrorMessageException e) {
            return ApiResult.resultWith(e.code, e.getMessage(), null);
        }


    }
    /**
     * 设置用户header数据
     *
     * @param request
     * @return
     */
    private User setUserHeaderInfo(HttpServletRequest request) {
        User user = new User();
        user.setAppVersion(RequestUtils.getHeader(request, Constant.APP_VERSION_KEY));
        user.setOsType(RequestUtils.getHeader(request, Constant.APP_SYSTEM_TYPE_KEY));
        user.setOsVersion(RequestUtils.getHeader(request, Constant.APP_OS_VERSION_KEY));
        user.setMobileType(RequestUtils.getHeader(request, Constant.APP_MOBILE_TYPE_KEY));
        user.setEquipmentId(RequestUtils.getHeader(request, Constant.APP_EQUIPMENT_NUMBER_KEY));
        return user;
    }


}
