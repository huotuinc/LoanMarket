/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service.user.impl;

import com.google.gson.JsonObject;
import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.common.utils.BuildSignUtils;
import com.huotu.loanmarket.service.enums.ConfigParameter;
import com.huotu.loanmarket.service.enums.MerchantConfigEnum;
import com.huotu.loanmarket.service.exceptions.ErrorMessageException;
import com.huotu.loanmarket.service.handler.JsonResponseHandler;
import com.huotu.loanmarket.service.model.user.UserInfoVo;
import com.huotu.loanmarket.service.service.merchant.MerchantCfgService;
import com.huotu.loanmarket.service.service.user.YouXinUserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author guomw
 * @date 2018/3/20
 */
@Service
public class YouXinUserServiceImpl implements YouXinUserService {

    private static final Log log = LogFactory.getLog(YouXinUserServiceImpl.class);
    @Autowired
    private Environment environment;
    @Autowired
    private MerchantCfgService merchantCfgService;

    private HttpClientBuilder httpClientBuilder;

    private RequestConfig requestConfig;

    @PostConstruct
    public void init() {
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(30000).setConnectionRequestTimeout(5000)
                .setSocketTimeout(30000).build();
        httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultRequestConfig(requestConfig).setUserAgent(Constant.DefaultAgent);

    }

    /**
     * 同步有信用户
     * @param mobile
     * @param zmfScoreType
     * @return
     * @throws ErrorMessageException
     */
    @Override
    public UserInfoVo syncUser(String mobile,int zmfScoreType) throws ErrorMessageException {

        StringBuilder syncUserUrl = new StringBuilder();
        Map<String, String> configItem = merchantCfgService.getConfigItem(Constant.MERCHANT_ID, MerchantConfigEnum.GENERAL);
        if (configItem != null) {
            String url = configItem.get(ConfigParameter.GeneralParameter.YOU_XIN_API_URL.getKey());
            syncUserUrl.append(url+"/api/user/syncUser");
        }


        Map<String, String> resultMap = new TreeMap<>();
        int resultCode = 2000;
        String resultMsg = "成功";
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            String timestamp = String.valueOf(System.currentTimeMillis());
            resultMap.put(Constant.APP_MERCHANT_ID_KEY.toLowerCase(), Constant.MERCHANT_ID.toString());
            resultMap.put("username", mobile);
            resultMap.put("zmfScore", String.valueOf(zmfScoreType));
            resultMap.put(Constant.APP_TIMESTAMP_KEY.toLowerCase(), timestamp);
            String strSign = BuildSignUtils.buildSignIgnoreEmpty(resultMap, null, Constant.SECRET_KEY);


            List<NameValuePair> nameValuePairList = new ArrayList<>();
            nameValuePairList.add(new BasicNameValuePair("username", mobile));
            nameValuePairList.add(new BasicNameValuePair("zmfScore", String.valueOf(zmfScoreType)));
            nameValuePairList.add(new BasicNameValuePair(Constant.APP_TIMESTAMP_KEY.toLowerCase(), timestamp));
            nameValuePairList.add(new BasicNameValuePair(Constant.SIGN_KEY.toLowerCase(), strSign));


            JsonObject jsonObject = execute(httpClient, syncUserUrl.toString(), nameValuePairList);
            resultCode = jsonObject.get("resultCode").getAsInt();
            resultMsg = jsonObject.get("resultMsg").getAsString();
            if (resultCode == 2000) {
                JsonObject data = jsonObject.getAsJsonObject("data");
                if (!data.isJsonNull()) {
                    UserInfoVo userInfoVo = new UserInfoVo();
                    userInfoVo.setCreditValue(0);
                    userInfoVo.setUserToken(!data.get("userToken").isJsonNull() ? data.get("userToken").getAsString() : "");
                    userInfoVo.setUserName(!data.get("userName").isJsonNull() ? data.get("userName").getAsString() : "");
                    userInfoVo.setUserId(!data.get("userId").isJsonNull() ? data.get("userId").getAsInt() : 0);
                    userInfoVo.setAuthStatus(!data.get("authIdCard").isJsonNull() ? data.get("authIdCard").getAsBoolean() ? 1 : 0 : 0);
                    return userInfoVo;
                }

            }
        } catch (IOException e) {
            log.error("syncUser error:"+e.getMessage(),e);
        }
        throw new ErrorMessageException(resultCode, resultMsg);
    }

    /**
     * 执行
     *
     * @param httpClient
     * @param url
     * @param nameValuePairs
     * @return
     * @throws IOException
     */
    private JsonObject execute(CloseableHttpClient httpClient, String url, List<NameValuePair> nameValuePairs) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(Constant.APP_VERSION_KEY, "1.0.0");
        httpPost.setHeader(Constant.APP_SYSTEM_TYPE_KEY, "h5");
        httpPost.setHeader(Constant.APP_MERCHANT_ID_KEY, Constant.MERCHANT_ID.toString());
        httpPost.setHeader(Constant.APP_CHANNELID_KEY, Constant.CHANNEL_KEY);
        httpPost.setEntity(
                EntityBuilder
                        .create()
                        .setContentType(ContentType.APPLICATION_FORM_URLENCODED).setContentEncoding("UTF-8")
                        .setParameters(nameValuePairs)
                        .build());

        return httpClient.execute(httpPost, new JsonResponseHandler());
    }
}
