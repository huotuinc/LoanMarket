package com.huotu.loanmarket.webapi.controller.sesame;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.SesameResultCode;
import com.huotu.loanmarket.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author hxh
 * @Date 2018/1/30 11:21
 */
@Controller
@RequestMapping("/api/sesame")
public class SesameController {
    @Autowired
    private UserService userService;

    @RequestMapping("/verifyIdAndName")
    @ResponseBody
    public ApiResult verifyIdAndName(@RequestHeader(Constant.APP_MERCHANT_ID_KEY) Integer merchantId,
                                     @RequestHeader(value = Constant.APP_USER_ID_KEY, required = false) Long userId, String name, String idCardNum) {
        if (userId == null) {
            return ApiResult.resultWith(SesameResultCode.USERID_EMPTY);
        }
        User user = userService.findByMerchantIdAndUserId(merchantId, userId);
        if (user == null) {
            return ApiResult.resultWith(SesameResultCode.USER_EMPTY);
        }
        //判断name和idCardNum是否匹配

        return null;
    }
}
