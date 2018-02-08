package com.huotu.loanmarket.webschedule.service;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.service.system.DataStatisticsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 统计相关
 * @author helloztt
 */
@Service
public class DataStatisticsSchedule {
    private static final Log log = LogFactory.getLog(DataStatisticsSchedule.class);
    @Autowired
    private DataStatisticsService dataStatisticsService;


    /**
     * 每整点统计上个小时的数据
     */
    @Scheduled(cron="0 1 * * * ? ")
    public void hourData(){
        //竟然没有 Merchant ？？那就暴力点吧。。
        dataStatisticsService.toStatisticsDataByHour(Constant.MERCHANT_ID);
    }

    /**
     * 每天10分的时候统计昨日的数据
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void dayData(){
        log.info("开始统计昨日数据");
        dataStatisticsService.toStatisticsDataByDay(Constant.MERCHANT_ID);
        log.info("昨日数据统计完成");
    }
}
