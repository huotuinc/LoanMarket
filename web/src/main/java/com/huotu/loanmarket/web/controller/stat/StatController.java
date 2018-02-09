package com.huotu.loanmarket.web.controller.stat;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.entity.system.DataStatisticsByDay;
import com.huotu.loanmarket.service.service.system.DataStatisticsSearcher;
import com.huotu.loanmarket.service.service.system.DataStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author helloztt
 */
@Controller
public class StatController {
    @Autowired
    private DataStatisticsService dataStatisticsService;

    @RequestMapping("/stat")
    public String getStatData(DataStatisticsSearcher dataStatisticsSearcher,Model model){
        Page<DataStatisticsByDay> dayReport = dataStatisticsService.getDayReport(dataStatisticsSearcher);
        model.addAttribute("statList",dayReport.getContent());
        model.addAttribute("totalRecord", dayReport.getTotalElements());
        model.addAttribute("totalPage", dayReport.getTotalPages());
        model.addAttribute("pageIndex", dataStatisticsSearcher.getPageIndex());
        return "stat/statistics_list";
    }

    /*@RequestMapping("/hour")
    public void hour(){
        dataStatisticsService.toStatisticsDataByHour(Constant.MERCHANT_ID);
    }

    @RequestMapping("/day")
    public void day(){
        dataStatisticsService.toStatisticsDataByDay(Constant.MERCHANT_ID);
    }*/
}
