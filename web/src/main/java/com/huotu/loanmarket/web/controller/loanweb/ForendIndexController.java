package com.huotu.loanmarket.web.controller.loanweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author hxh
 * @date 2017-10-30
 */
@Controller
@RequestMapping("/forend")
public class ForendIndexController {
    @RequestMapping("/index")
    public String index(){
       return "home";
    }
}

