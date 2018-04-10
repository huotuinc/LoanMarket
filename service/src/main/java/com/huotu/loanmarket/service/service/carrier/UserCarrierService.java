package com.huotu.loanmarket.service.service.carrier;

import com.google.gson.JsonObject;
import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.model.carrier.UserCarrierVo;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author luyuanyuan on 2017/11/22.
 */
public interface UserCarrierService {


    /**
     * 魔盒结果查询
     *
     * @param taskId     任务id
     * @param orderId    订单id
     * @return
     * @throws IOException
     */
    ApiResult queryResult(String taskId, String orderId, Integer merchantId) throws IOException;


    JsonObject magicReport(String taskId, Integer merchantId) throws IOException;


    UserCarrierVo carrierShow(String orderId);
}
