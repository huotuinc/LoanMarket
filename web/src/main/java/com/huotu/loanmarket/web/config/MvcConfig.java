package com.huotu.loanmarket.web.config;

import com.huotu.loanmarket.common.utils.StringUtilsExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.Arrays;
import java.util.List;

/**
 * @author allan
 * @date 19/10/2017
 */
@Configuration
@EnableWebMvc
@ComponentScan({
        "com.huotu.loanmarket.web",
        "com.huotu.loanmarket.service"
})
public class MvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 静态资源处理,加在这里
     */
    private static String[] STATIC_RESOURCE_PATH = {
            "resource"
    };
    @Autowired
    private Environment environment;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 静态资源设置
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

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(backendTemplateResolver());
        registry.viewResolver(forendTemplateResolver());
    }

    /**
     * html解析器
     *
     * @return
     */
    private ViewResolver backendTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/templates/backend/");
        resolver.setSuffix(".html");
        resolver.setApplicationContext(applicationContext);
        resolver.setCharacterEncoding(StringUtilsExt.ENCODING_UTF8);
        //设置缓存
        if (environment.acceptsProfiles("development")) {
            resolver.setCacheable(false);
        }

        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(resolver);

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(engine);
        viewResolver.setOrder(1);
        viewResolver.setContentType("text/html;charset=utf-8");
        viewResolver.setCharacterEncoding(StringUtilsExt.ENCODING_UTF8);

        return viewResolver;
    }

    /**
     * html解析器
     *
     * @return
     */
    private ViewResolver forendTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("/templates/forend/");
        resolver.setSuffix(".html");
        resolver.setApplicationContext(applicationContext);
        resolver.setCharacterEncoding(StringUtilsExt.ENCODING_UTF8);
        //设置缓存
        if (environment.acceptsProfiles("development")) {
            resolver.setCacheable(false);
        }

        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(resolver);

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(engine);
        viewResolver.setOrder(2);
        viewResolver.setContentType("text/html;charset=utf-8");
        viewResolver.setCharacterEncoding(StringUtilsExt.ENCODING_UTF8);

        return viewResolver;
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
}
