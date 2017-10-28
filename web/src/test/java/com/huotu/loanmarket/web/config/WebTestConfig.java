package com.huotu.loanmarket.web.config;

import com.huotu.loanmarket.service.config.ServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by allan on 29/10/2017.
 */
@Configuration
@Import({
        MvcConfig.class,
        ServiceConfig.class
})
public class WebTestConfig {
}
