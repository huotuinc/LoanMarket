package com.huotu.loanmarket.service.service.ds;

import com.huotu.loanmarket.common.utils.ApiResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author luyuanyuan on 2018/2/1.
 */
public interface DsService {

    /**
     * 电商结果查询
     *
     * @param taskId     任务id
     * @param orderId    订单id
     * @return
     * @throws IOException
     */
    @Transactional
    ApiResult queryResult(String taskId, String orderId, Integer merchantId) throws IOException;
}
