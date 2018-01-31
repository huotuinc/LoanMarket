package com.huotu.loanmarket.service.service.sesame.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huotu.loanmarket.common.httputils.HttpClientUtil;
import com.huotu.loanmarket.common.httputils.HttpResult;
import com.huotu.loanmarket.service.config.LoanMarkConfigProvider;
import com.huotu.loanmarket.service.model.sesame.SesameConfig;
import com.huotu.loanmarket.service.service.sesame.SesameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author hxh
 * @Date 2018/1/30 16:17
 */
@Service
public class SesameServiceImpl implements SesameService {
    @Autowired
    private LoanMarkConfigProvider loanMarkConfigProvider;

    @Override
    public boolean checkNameAndIdCardNum(Integer merchantId, String name, String idCardNum) {
        SesameConfig sesameConfig = loanMarkConfigProvider.getSesameConfig(merchantId);
        Map<String, Object> requestMap = new TreeMap<>();
        requestMap.put("api_id", sesameConfig.getApiId());
        requestMap.put("api_secret", sesameConfig.getApiSecret());
        requestMap.put("name", name);
        requestMap.put("id_number", idCardNum);
        HttpResult httpResult = HttpClientUtil.getInstance().postFile(sesameConfig.getVerifyIdAndNameUrl(), requestMap);
        JSONObject jsonObject = JSON.parseObject(httpResult.getHttpContent());
        return jsonObject != null && "OK".equals(jsonObject.getString("status"));
    }
}
