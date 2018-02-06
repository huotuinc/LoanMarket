package com.huotu.loanmarket.webapi.controller.user;

import com.huotu.loanmarket.common.utils.ApiResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 征信报告
 *
 * @author wm
 */
@Controller
@RequestMapping(value = "/api/user/report", method = RequestMethod.POST)
public class CreditReportController {
    /**
     * 金融黑名单(基于同盾)
     *
     * @return 结果
     */
    @RequestMapping(value = "/financialBlacklist", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult financialBlacklist() {
        return null;
    }

    // TODO: 2018/2/5 其他报告内容
}