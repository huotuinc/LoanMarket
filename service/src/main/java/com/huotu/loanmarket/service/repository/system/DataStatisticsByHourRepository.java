package com.huotu.loanmarket.service.repository.system;

import com.huotu.loanmarket.service.entity.system.DataStatisticsByDay;
import com.huotu.loanmarket.service.entity.system.DataStatisticsByHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

/**
 * @author helloztt
 */
public interface DataStatisticsByHourRepository extends JpaRepository<DataStatisticsByHour, Integer> {

    /**
     * 统计区间范围内的数据
     * @param merchantId
     * @param beginTime
     * @param endTime
     * @return
     */
    @Query("select new com.huotu.loanmarket.service.entity.system.DataStatisticsByDay(" +
            "sum(hourDate.orderAmount),sum(hourDate.userCount),sum(hourDate.orderCount),sum(hourDate.orderPayCount)" +
            ",sum(hourDate.authSuccessCount),sum(hourDate.authFailureCount),max(hourDate.endTime))" +
            " from DataStatisticsByHour hourDate" +
            " where hourDate.merchantId = ?1 and hourDate.beginTime >= ?2 and hourDate.endTime <= ?3")
    DataStatisticsByDay sumByBeginTime(Integer merchantId, LocalDateTime beginTime, LocalDateTime endTime);
}
