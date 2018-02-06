package com.huotu.loanmarket.service.service.tongdun;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;


import com.huotu.loanmarket.service.model.tongdun.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 贷前风险检查
 * 工具类
 * @author wm
 */
@Service
public class PreLoanRiskService {
    private static final Log log = LogFactory.getLog(PreLoanRiskService.class);
    private static final int HTTP_OK = 200;
    private SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

    /**
     * 申请报告
     *
     * @param tongdunConfig
     * @param submitRequest
     * @return
     */
    public PreLoanSubmitResponse apply(TongdunApiConfig tongdunConfig, PreLoanSubmitRequest submitRequest) {
        List<String> nullFields = new ArrayList<>();
        if (StringUtils.isEmpty(submitRequest.getName())) {
            nullFields.add("name");
        }
        if (StringUtils.isEmpty(submitRequest.getMobile())) {
            nullFields.add("mobile");
        }
        if (StringUtils.isEmpty(submitRequest.getIdNumber())) {
            nullFields.add("idNumber");
        }
        if (nullFields.size() > 0) {
            throw new IllegalArgumentException(MessageFormat.format("[PreLoanRiskService] apply:{0} not be null", String.join(",", nullFields)));
        }
        PreLoanSubmitResponse submitResponse = new PreLoanSubmitResponse();
        try {
            String urlString = new StringBuilder().append(tongdunConfig.getSubmitUrl()).append("?partner_code=").append(tongdunConfig.getPartnerCode())
                    .append("&partner_key=").append(tongdunConfig.getPartnerKey()).append("&app_name=").append(tongdunConfig.getPartnerApp()).toString();
            URL url = new URL(urlString);
            // 组织请求参数
            String postBody = submitRequest.toString();
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            //设置https
            conn.setSSLSocketFactory(ssf);
            // 设置长链接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置连接超时
            conn.setConnectTimeout(1000);
            // 设置读取超时，建议设置为3000ms。若同时调用了信息核验服务，请与客户经理协商确认具体时间”
            conn.setReadTimeout(3000);
            // 提交参数
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.getOutputStream().write(postBody.getBytes());
            conn.getOutputStream().flush();
            int responseCode = conn.getResponseCode();
            if (responseCode == HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                return JSON.parseObject(result.toString().trim(), PreLoanSubmitResponse.class);
            }
        } catch (Exception e) {
            submitResponse.setReason_desc("apply throw exception, details: " + e.getMessage());
            submitResponse.setSuccess(false);
            log.error("[PreLoanRiskService] apply throw exception, details: " + e);
        }
        return submitResponse;
    }

    /**
     * 查询报告
     *
     * @param tongdunConfig
     * @param reportId
     * @return
     */
    public String queryReport(TongdunApiConfig tongdunConfig, String reportId) {
        try {
            String urlString = new StringBuilder().append(tongdunConfig.getQueryUrl()).append("?partner_code=").append(tongdunConfig.getPartnerCode()).
                    append("&partner_key=").append(tongdunConfig.getPartnerKey()).append("&report_id=").append(reportId).toString();
            URL url = new URL(urlString);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            //设置https
            conn.setSSLSocketFactory(ssf);
            // 设置长链接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置连接超时
            conn.setConnectTimeout(1000);
            // 设置读取超时
            conn.setReadTimeout(500);
            // 提交参数
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                return result.toString().trim();
            }
        } catch (Exception e) {
            log.error("[PreLoanRiskService] queryReport throw exception, details: " + e);
        }
        return null;
    }

    /**
     * 报告查询实体转换
     *
     * @param reportContent
     * @return
     */
    public PreLoanQueryResponse parseReport(String reportContent) {
        if (StringUtils.isEmpty(reportContent)) {
            return null;
        }
        try {
            return JSON.parseObject(reportContent, PreLoanQueryResponse.class);
        } catch (Exception e) {
        }
        return null;
    }
}

