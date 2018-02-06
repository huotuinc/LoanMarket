package com.huotu.loanmarket.service.service.tongdun;

import com.huotu.loanmarket.service.entity.tongdun.TongdunRequestLog;
import com.huotu.loanmarket.service.model.tongdun.TongdunLogSearchCondition;
import com.huotu.loanmarket.service.model.tongdun.TongdunReviewResult;
import com.huotu.loanmarket.service.model.tongdun.report.ReportDetailVo;
import org.springframework.data.domain.Page;

/**
 * @author wm
 */
public interface TongdunRequestLogService {
    /**
     * 保存同盾请求结果日志
     * @param reviewResult
     * @return
     */
    TongdunRequestLog save(TongdunReviewResult reviewResult);

    /**
     * 获取请求日志列表
     * @param searchCondition
     * @return
     */
    Page<TongdunRequestLog> findAll(TongdunLogSearchCondition searchCondition);

    /**
     * 根据订单号获取报告信息
     * @param orderId 订单号
     * @return 报告日志
     */
    TongdunRequestLog findByOrderId(String orderId);

    /**
     * 根据自增id获取报告信息
     * @param id
     * @return
     */
    TongdunRequestLog findById(Long id);

    /**
     * 根据用户id获取报告信息
     * @param userId
     * @return
     */
    TongdunRequestLog findByUserId(Long userId);


    ReportDetailVo getReportDetail(Long id);

    /**
     * 转换日志到报告实体
     * @param requestLog
     * @return
     */
    ReportDetailVo convertRequestLogToReport(TongdunRequestLog requestLog);
}