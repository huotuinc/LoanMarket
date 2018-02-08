package com.huotu.loanmarket.web.controller.base;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.service.system.DataStatisticsService;
import com.huotu.loanmarket.service.service.upload.StaticResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author allan
 * @date 19/10/2017
 */
@Controller
public class IndexController {
    @Autowired
    private StaticResourceService resourceService;
    @Autowired
    private DataStatisticsService dataStatisticsService;

    @ModelAttribute(value = "resourceService")
    public StaticResourceService resourceService() {
        return resourceService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping("/indexCount")
    public String getData(Model model){
        model.addAttribute("data",dataStatisticsService.todayData(Constant.MERCHANT_ID));
        return "indexcount";
    }

    @RequestMapping("/test")
    public void test(){
        dataStatisticsService.toStatisticsDataByHour(Constant.MERCHANT_ID);
    }

}
