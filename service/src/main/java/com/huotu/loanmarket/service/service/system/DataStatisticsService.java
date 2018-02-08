package com.huotu.loanmarket.service.service.system;

import com.huotu.loanmarket.service.entity.system.DataStatisticsByDay;
import com.huotu.loanmarket.service.model.system.DataStatisticsVo;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

/**
 * 统计相关
 * @author helloztt
 */
public interface DataStatisticsService {
    void cleanCount();
    /**
     * 获取今日统计数据
     * @param merchantId 商户Id
     * @return
     */
    DataStatisticsVo todayData(int merchantId);
    /**
     * 每小时统计数据并记录存到数据库
     *
     * @param merchantId 商户Id
     * @return 统计
     */
    void toStatisticsDataByHour(int merchantId);

    /**
     * 每天统计数据并记录存到数据库
     * @param merchantId 商户Id
     * @return
     */
    void toStatisticsDataByDay(int merchantId);

    Page<DataStatisticsByDay> getDayReport(DataStatisticsSearcher searcher);
}
