package com.huotu.loanmarket.service.service.carrier;

import com.huotu.loanmarket.common.utils.ApiResult;
import org.springframework.transaction.annotation.Transactional;

import javax.json.JsonObject;
import java.io.IOException;

/**
 * @author luyuanyuan on 2017/11/22.
 */
public interface UserCarrierService {


    /**
     * 魔盒结果查询
     *
     * @param taskId     任务id
     * @param userId     用户id
     * @return
     * @throws IOException
     */
    @Transactional
    ApiResult queryResult(String taskId, Long userId, Long merchantId) throws IOException;


    JsonObject magicReport(String taskId, Long userId, Long merchantId) throws IOException;


}
