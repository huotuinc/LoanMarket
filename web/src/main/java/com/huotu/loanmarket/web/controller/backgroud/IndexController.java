package com.huotu.loanmarket.web.controller.backgroud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author allan
 * @date 19/10/2017
 */
@Controller
public class IndexController extends BaseController {
    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
