package com.huotu.loanmarket.webapi.controller.other;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 协议等静态页面
 * @author wm
 */
@Controller
@RequestMapping("/api/other")
public class OtherController {

    @RequestMapping("/{html}")
    public String getHtml(@PathVariable String html) {
        return "other/" + html;
    }
}