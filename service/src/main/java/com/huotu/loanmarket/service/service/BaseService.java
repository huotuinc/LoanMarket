/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.service.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author helloztt
 */
@Service
public class BaseService {
    private static final Log log = LogFactory.getLog(BaseService.class);
    @Autowired
    private Environment environment;



    /**
     * 获取当前服务平台home地址，必须以斜杠结尾。
     * 可通过修改系统属性com.huotu.huobanplus.uri改变该值。
     *
     * @return URI
     */
    public String serviceHomeURI() {
        return environment.getProperty("com.huotu.loanmarket.manage.uri", "http://superloan.51flashmall.com/");
    }

    /**
     * api接口请求地址
     *
     * @return
     */
    public String apiHomeURI() {
        return environment.getProperty("com.huotu.loanmarket.app.uri", "http://localhost:8080/");
    }

    /**
     * h5页面请求地址
     *
     * @return
     */
    public String h5HomeURI() {
        return environment.getProperty("com.huotu.loanmarket.h5.uri", "http://superloan.51flashmall.com/");
    }

    /**
     * 获取当前服务平台的工作域名
     *
     * @return domain
     * @since 1.4.2
     */
    public String domain() {
        return environment.getProperty("loanmarket.domain", "51flashmall.com");
    }

}