package com.huotu.loanmarket.service.service.tongdun;

import com.huotu.loanmarket.common.utils.ApiResult;

/**
 * 同盾报告(金融黑名单)
 * @author wm
 */
public interface TongdunReportService {
    /**
     * 获取风控报告
     * @param orderId 支付订单号
     * @param userId 用户id
     * @return 报告内容
     */
    ApiResult getRiskReport(String orderId,Long userId);
}