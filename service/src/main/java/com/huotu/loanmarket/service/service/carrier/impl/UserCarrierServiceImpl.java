package com.huotu.loanmarket.service.service.carrier.impl;

import com.huotu.loanmarket.common.utils.ApiResult;
import com.huotu.loanmarket.service.service.carrier.UserCarrierService;
import org.springframework.stereotype.Service;

import javax.json.JsonObject;
import java.io.IOException;

/**
 * @author luyuanyuan on 2018/1/30.
 */
@Service
public class UserCarrierServiceImpl implements UserCarrierService {


    @Override
    public ApiResult queryResult(String taskId, Long userId, Long merchantId) throws IOException {
        return null;
    }

    @Override
    public JsonObject magicReport(String taskId, Long userId, Long merchantId) throws IOException {
        return null;
    }

}
