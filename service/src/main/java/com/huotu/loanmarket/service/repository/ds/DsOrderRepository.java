package com.huotu.loanmarket.service.repository.ds;

import com.huotu.loanmarket.service.entity.ds.DsOrder;
import com.huotu.loanmarket.service.model.ds.DsOrderVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author luyuanyuan on 2018/2/1.
 */
public interface DsOrderRepository extends JpaRepository<DsOrder, Long> {
    @Query(value = "select new com.huotu.loanmarket.service.model.ds.DsOrderVo(count(e.orderId) ,cast(avg(e.orderAmount) as decimal(10,2))" +
            ",min(e.orderTime),max(e.orderTime))" +
            "from DsOrder e where e.orderId = ?1")
    DsOrderVo stats(String orderId);
}
