package com.huotu.loanmarket.service.repository.tongdun;

import com.huotu.loanmarket.service.entity.tongdun.TongdunRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wm
 */
@Repository
public interface TongdunRequestLogRepository extends JpaRepository<TongdunRequestLog, Long>, JpaSpecificationExecutor<TongdunRequestLog> {
    /**
     * 根据用户id获取最新的报告请求日志
     *
     * @param userId
     * @return
     */
    List<TongdunRequestLog> findByUserIdOrderByIdDesc(Long userId);

    /**
     * 根据用户id获取最新的报告请求日志
     *
     * @param userId
     * @return
     */
    TongdunRequestLog findFirstByUserIdOrderByIdDesc(Long userId);

    /**
     * 根据订单id获取最新的报告请求日志
     *
     * @param orderId
     * @return 日志
     */
    TongdunRequestLog findFirstByOrderIdOrderByIdDesc(String orderId);

    @Query("select t from TongdunRequestLog t where t.userId=?1 and t.state=?2 order by t.id desc")
    List<TongdunRequestLog> findByUserIdAndState(Long userId, Integer state);
}