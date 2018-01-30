/*
 * *
 *  * @author guomw
 *  *
 *
 */

package com.huotu.loanmarket.webapi.config;

import com.huotu.loanmarket.common.utils.ViewResolverUtils;
import com.huotu.loanmarket.webapi.interceptor.AppInterceptor;
import com.huotu.loanmarket.webapi.interceptor.AppLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.Arrays;
import java.util.List;

/**
 * @author guomw
 * @date 29/01/2018
 */
@Configuration
@EnableWebMvc
@ComponentScan({
        "com.huotu.loanmarket.webapi.controller",
        "com.huotu.loanmarket.service.service"
})
public class MvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 静态资源处理,加在这里
     */
    private static String[] STATIC_RESOURCE_PATH = {
            "resource",
            "script"
    };

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Environment environment;


    /**
     * 禁止拦截静态资源
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        for (String resourcePath : MvcConfig.STATIC_RESOURCE_PATH) {
            registry.addResourceHandler("/" + resourcePath + "/**").addResourceLocations("/" + resourcePath + "/");
        }
    }

    /**
     * for upload
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON));

        converters.add(converter);
    }

    /**
     * 默认拦截器
     *
     * @return
     */
    @Bean
    public AppInterceptor appInterceptor() {
        return new AppInterceptor();
    }

    /**
     * 登录拦截器
     *
     * @return
     */
    @Bean
    public AppLoginInterceptor appLoginInterceptor() {
        return new AppLoginInterceptor();
    }


    /**
     * 判断拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截
        this.addLoginAuthInterceptor(registry);

        //签名授权拦截
        this.addSignatureInterceptor(registry);

    }

    /**
     * 登录拦截器
     *
     * @param registry
     */
    private void addLoginAuthInterceptor(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(appLoginInterceptor());

        /**
         * 以下规则接口，必须进行登录验证
         */
        registration.addPathPatterns("/api/user/**");


        /**
         * 以下规则接口，不需要进行登录验证
         */
        registration.excludePathPatterns("/api/user/sendVerifyCode")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/user/updatePassword");
    }

    /**
     * 签名授权拦截
     *
     * @param registry
     */
    private void addSignatureInterceptor(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(appLoginInterceptor());
        /**
         * 以下规则接口，必须进行签名验证
         */
        registration.addPathPatterns("/api/**");

        /**
         * 以下规则接口，不进行签名验证
         */
        registration.excludePathPatterns("/api/sys/test");

    }


    /**
     * 视图显示Resolver
     *
     * @param registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        registry.viewResolver(viewResolver());
    }

    /**
     * thymeleaf解析
     *
     * @return
     */
    @Bean
    public ThymeleafViewResolver viewResolver() {
        return new ViewResolverUtils().getThymeleafViewResolver(webApplicationContext.getServletContext(), "/views/", environment);
    }
}
