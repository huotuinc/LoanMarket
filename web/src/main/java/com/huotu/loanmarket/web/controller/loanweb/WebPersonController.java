package com.huotu.loanmarket.web.controller.loanweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author hxh
 * @date 2017-11-01
 */
@Controller
@RequestMapping("/forend/person")
public class WebPersonController {
    @RequestMapping("/center")
    public String getPersonInfo(){
        return "forend/user";
    }
}
