/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2016-2018. All rights reserved.
 */

package com.huotu.loanmarket.common.utils;

import com.huotu.loanmarket.common.Constant;
import org.springframework.core.env.Environment;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

/**
 * @author guomw
 * @date 10/01/2018
 */
public class ViewResolverUtils {

    /**
     *
     * @param servletContext
     * @param prefix
     * @return
     */
    public ThymeleafViewResolver getThymeleafViewResolver(ServletContext servletContext,String prefix,Environment environment) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        SpringTemplateEngine engine = new SpringTemplateEngine();
        ServletContextTemplateResolver rootTemplateResolver = new ServletContextTemplateResolver(servletContext);
        rootTemplateResolver.setPrefix(prefix);
        rootTemplateResolver.setSuffix(".html");
        rootTemplateResolver.setTemplateMode(TemplateMode.HTML);
        rootTemplateResolver.setCharacterEncoding("UTF-8");

        if (environment.acceptsProfiles(Constant.PROFILE_DEV)) {
            rootTemplateResolver.setCacheable(false);
        }

        engine.setTemplateResolver(rootTemplateResolver);
        resolver.setTemplateEngine(engine);
        resolver.setOrder(100);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setContentType("text/html;charset=utf-8");
        return resolver;
    }
}
