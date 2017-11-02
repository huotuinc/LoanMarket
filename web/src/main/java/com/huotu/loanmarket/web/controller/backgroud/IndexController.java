package com.huotu.loanmarket.web.controller.backgroud;

import com.huotu.loanmarket.common.utils.CookieHelper;
import com.huotu.loanmarket.service.service.VerifyCodeService;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * @author allan
 * @date 19/10/2017
 */
@Controller
public class IndexController {
    @Autowired
    private VerifyCodeService verifyCodeService;
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }
    @RequestMapping("/verifyCode")
    public ApiResult sendVerifyCode(String mobile, HttpServletResponse response){
        String message = "您好，您的验证码是{code}";
        if (verifyCodeService.sendCode(mobile, message)) {
            CookieHelper.setCookie(response, "user", mobile, ".loanmarket.51flashmall.com");
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST);
    }
}
