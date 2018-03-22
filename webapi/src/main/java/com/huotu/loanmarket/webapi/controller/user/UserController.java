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
import com.huotu.loanmarket.service.config.GeneralConfig;
import com.huotu.loanmarket.service.config.LoanMarkConfigProvider;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.service.enums.UserResultCode;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.model.PageListView;
import com.huotu.loanmarket.service.model.user.UserInfoVo;
import com.huotu.loanmarket.service.model.user.UserInviteVo;
import com.huotu.loanmarket.service.service.upload.StaticResourceService;
import com.huotu.loanmarket.service.service.user.UserService;
import com.huotu.loanmarket.service.service.user.YouXinUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author guomw
 * @date 30/01/2018
 */
@Controller
@RequestMapping(value = "/api/user", method = RequestMethod.POST)
public class UserController {

    private static final Log log = LogFactory.getLog(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private StaticResourceService staticResourceService;

    @Autowired
    private LoanMarkConfigProvider loanMarkConfigProvider;
    @Autowired
    private YouXinUserService youXinUserService;

    /**
     * 根据手机号、密码实现用户登录
     * 密码跟短信验证码二选一 必填
     *
     * @param username  用户名
     * @param input     密码(md5)或验证码
     * @param loginType 登录方式[0:密码登录 1:验证码登录]
     * @param inviter 邀请者
     * @param request
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ApiResult login(@RequestParam String username,
                           @RequestParam String input,
                           @RequestParam(required = false, defaultValue = "0") int loginType,
                           @RequestParam(required = false, defaultValue = "0") Long inviter,
                           HttpServletRequest request) {

        if (!RegexUtils.checkMobile(username)) {
            return ApiResult.resultWith(UserResultCode.CODE1);
        }
        User user;
        //0:密码登录
        if (loginType == 0) {
            if (StringUtils.isEmpty(input) || input.length() != Constant.PASS_WORD_LENGTH) {
                return ApiResult.resultWith(UserResultCode.CODE2);
            }
        } else {
            if (StringUtils.isEmpty(input) || input.length() != Constant.VERIFY_CODE_LENGTH) {
                return ApiResult.resultWith(UserResultCode.CODE9);
            }
        }

        try {
            user = userService.login(username, input, loginType, inviter,request);
        } catch (ErrorMessageException e) {
            return ApiResult.resultWith(e.code, e.getMessage());
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserId(user.getUserId());
        userInfoVo.setUserName(user.getUserName());
        userInfoVo.setUserToken(user.getUserToken());
        userInfoVo.setHeadimg(user.getHeadimg());
        userInfoVo.setAuthStatus(user.getAuthStatus().getCode());
        return ApiResult.resultWith(AppCode.SUCCESS, userInfoVo);

    }

    /**
     * 验证码登录
     * @param username
     * @param input
     * @param inviter
     * @param request
     * @return
     */
    @RequestMapping("/loginByVerifyCode")
    @ResponseBody
    public ApiResult loginByVerifyCode(@RequestParam String username,
                                       @RequestParam String input,
                                       @RequestParam(required = false, defaultValue = "0") Long inviter,
                                       HttpServletRequest request){

        int loginType=1;
        if (!RegexUtils.checkMobile(username)) {
            return ApiResult.resultWith(UserResultCode.CODE1);
        }

        if (StringUtils.isEmpty(input) || input.length() != Constant.VERIFY_CODE_LENGTH) {
            return ApiResult.resultWith(UserResultCode.CODE9);
        }
        try {
            User user=userService.login(username, input, loginType, inviter,request);
            UserInfoVo userInfoVo = new UserInfoVo();
            userInfoVo.setUserId(user.getUserId());
            userInfoVo.setUserName(user.getUserName());
            userInfoVo.setUserToken(user.getUserToken());
            userInfoVo.setHeadimg(user.getHeadimg());
            userInfoVo.setAuthStatus(user.getAuthStatus().getCode());

            //登录成功后，同步有信用户数据
            UserInfoVo yxUserInfo=youXinUserService.syncUser(username);




            Map<String,Object> map= new HashMap<>();
            map.put("userInfo",userInfoVo);
            map.put("yxUserInfo",yxUserInfo);
            return ApiResult.resultWith(AppCode.SUCCESS, map);

        } catch (ErrorMessageException e) {
            return ApiResult.resultWith(e.code, e.getMessage());
        }
    }


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
            user = userService.register(user, verifyCode);
            UserInfoVo userInfoVo = new UserInfoVo();
            userInfoVo.setUserId(user.getUserId());
            userInfoVo.setUserName(user.getUserName());
            userInfoVo.setUserToken(user.getUserToken());
            userInfoVo.setHeadimg(user.getHeadimg());
            userInfoVo.setAuthStatus(user.getAuthStatus().getCode());
            return ApiResult.resultWith(AppCode.SUCCESS, userInfoVo);
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

    /**
     * 用户修改（忘记）密码接口
     *
     * @param username
     * @param newPassword
     * @param verifyCode
     * @return
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public ApiResult updatePassword(String username,
                                    String newPassword, String verifyCode) {

        if (!RegexUtils.checkMobile(username)) {
            return ApiResult.resultWith(UserResultCode.CODE1);
        }

        //判断密码长度
        if (StringUtils.isEmpty(newPassword) || newPassword.length() != Constant.PASS_WORD_LENGTH) {
            return ApiResult.resultWith(UserResultCode.CODE2);
        }
        //判断验证码长度
        if (StringUtils.isEmpty(verifyCode) || verifyCode.length() != Constant.VERIFY_CODE_LENGTH) {
            return ApiResult.resultWith(UserResultCode.CODE9);
        }
        try {
            userService.updatePassword(username, newPassword, verifyCode);
            return ApiResult.resultWith(AppCode.SUCCESS);
        } catch (ErrorMessageException e) {
            return ApiResult.resultWith(e.code, e.getMessage());
        }
    }

    /**
     * 用户中心 (可不登录调用)
     *
     * @param userId    用户ID
     * @param userToken 用户token
     * @return
     */
    @RequestMapping("/center")
    @ResponseBody
    public ApiResult userCenter(@RequestHeader(value = Constant.APP_USER_ID_KEY, required = false, defaultValue = "0") Long userId,
                                @RequestHeader(value = Constant.APP_USER_TOKEN_KEY, required = false, defaultValue = "") String userToken) {

        Long certifiedCount = 0L, unverifiedCount = 0L;
        if (userService.checkLoginToken(Constant.MERCHANT_ID, userId, userToken)) {
            try {
                certifiedCount = userService.countByMyInvite(userId, true);
                unverifiedCount = userService.countByMyInvite(userId, false);
            } catch (Exception e) {
                log.error(e);
            }
        }
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("certifiedCount", certifiedCount);
        map.put("unverifiedCount", unverifiedCount);
        return ApiResult.resultWith(AppCode.SUCCESS, map);
    }

    /**
     * 我的邀请列表
     *
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/myInviteList")
    @ResponseBody
    public ApiResult myInviteList(@RequestHeader(Constant.APP_USER_ID_KEY) Long userId,
                                  @RequestParam(required = false, defaultValue = "1") int authType,
                                  @RequestParam(required = false, defaultValue = "1") int pageIndex,
                                  @RequestParam(required = false, defaultValue = Constant.PAGE_SIZE_STR) int pageSize
    ) {
        pageIndex = pageIndex <= 0 ? 1 : pageIndex;


        PageListView<UserInviteVo> result = userService.getMyInviteList(userId, authType == 1, true, pageIndex, pageSize);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("list", result.getList());
        return ApiResult.resultWith(AppCode.SUCCESS, map);
    }

    @RequestMapping("/img")
    @ResponseBody
    public ApiResult uploadImg(@RequestHeader(Constant.APP_USER_ID_KEY) Long userId, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException, URISyntaxException {
        String fileName = file.getOriginalFilename();
        if (com.alipay.api.internal.util.StringUtils.isEmpty(fileName)) {
            throw new FileNotFoundException("未上传任何图片");
        }
        String path = StaticResourceService.HEAD_IMG +
                "head-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS")) + staticResourceService.getSuffix(fileName);
        URI uri = staticResourceService.uploadResource(path, file.getInputStream());
        //更新用户头像
        userService.setHeadImg(userId, uri.toString());
        return ApiResult.resultWith(AppCode.SUCCESS.getCode(), AppCode.SUCCESS.getName(), uri.toString());
    }

    /**
     * 获取注册页面
     *
     * @return
     */
    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String registerPage(Model model) {

        GeneralConfig generalConfig = loanMarkConfigProvider.getGeneralConfig(Constant.MERCHANT_ID);
        model.addAttribute("appUrl", generalConfig.getYingyongbaoAddr());
        return "register/register.html";
    }
}
