package com.huotu.loanmarket.webapi.controller.user;

import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.service.tongdun.TongdunReportService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/report")
public class CreditReportController {
    @Autowired
    private TongdunReportService tongdunReportService;

    /**
     * 金融黑名单页面(基于同盾)
     *
     * @return 结果
     */
    @RequestMapping(value = "/financialBlack", method = RequestMethod.GET)
    public String financialBlack() {
        return "report/financialBlack";
    }

    /**
     * 获取金融黑名单数据(基于同盾)
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/getFinancialBlack", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getFinancialBlack(String orderId) {
        return tongdunReportService.getRiskReport(orderId);
    }
}