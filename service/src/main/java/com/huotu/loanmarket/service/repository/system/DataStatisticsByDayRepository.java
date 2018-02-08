package com.huotu.loanmarket.service.repository.system;

import com.huotu.loanmarket.service.entity.system.DataStatisticsByDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author helloztt
 */
public interface DataStatisticsByDayRepository extends JpaRepository<DataStatisticsByDay,Integer>,JpaSpecificationExecutor<DataStatisticsByDay> {

    @Query("select sum(dayData.userCount),sum(dayData.orderAmount) from DataStatisticsByDay dayData where dayData.merchantId = ?1")
    List<Object[]> sumUserAndAmount(int merchantId);
}
