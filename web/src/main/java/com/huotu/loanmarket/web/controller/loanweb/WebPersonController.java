package com.huotu.loanmarket.web.controller.loanweb;

import com.huotu.loanmarket.service.entity.LoanUser;
import com.huotu.loanmarket.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author hxh
 * @date 2017-11-01
 */
@Controller
@RequestMapping("/forend/person")
public class WebPersonController {
    @Autowired
    private UserService userService;
    @RequestMapping("/center")
    public String getPersonInfo(Model model){
        String mobile = userService.checkLogin();
        boolean flag = false;
        if(mobile!=null){
            LoanUser user = userService.checkLogin(mobile);
            model.addAttribute("user",user);
            flag = true;
        }
        model.addAttribute("flag",flag);
        return "forend/user";
    }
    @RequestMapping("/about")
    public String about(){
        return "forend/about";
    }
    @RequestMapping("/set")
    public String setUp(){
        return "forend/setUp";
    }
    @RequestMapping("/agreement")
    public String agreement(){
        return "forend/agreement";
    }
}
