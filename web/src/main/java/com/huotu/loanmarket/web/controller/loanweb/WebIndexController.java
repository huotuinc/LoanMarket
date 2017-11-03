package com.huotu.loanmarket.web.controller.loanweb;

import com.huotu.loanmarket.common.utils.CookieHelper;
import com.huotu.loanmarket.service.entity.LoanProject;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.service.service.UserService;
import com.huotu.loanmarket.service.service.VerifyCodeService;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author hxh
 * @date 2017-10-30
 */
@Controller
@RequestMapping("/forend")
public class WebIndexController {
    @Autowired
    private VerifyCodeService verifyCodeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @RequestMapping("/index")
    public String index(Model model){
        List<LoanProject> hotProject = projectService.getHotProject();
        List<LoanProject> newProject = projectService.getNewProject();
        model.addAttribute("hotProject",hotProject);
        model.addAttribute("newProject",newProject);
        return "forend/home";
    }

    @RequestMapping("/checkLogin")
    @ResponseBody
    public ApiResult checkLogin(){
        boolean result = userService.checkLogin();
        if(result){
            return ApiResult.resultWith(ResultCodeEnum.SUCCESS,"用户已登录",null);
        }
        return ApiResult.resultWith(ResultCodeEnum.SYSTEM_BAD_REQUEST,"用户未登录",null);
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

