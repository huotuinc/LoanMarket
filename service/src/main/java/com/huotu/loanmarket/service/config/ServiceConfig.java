package com.huotu.loanmarket.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author allan
 * @date 19/10/2017
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.huotu.loanmarket.service")
@EnableJpaRepositories(
        basePackages = "com.huotu.loanmarket.service.repositoryhxh"
)
@ImportResource({"classpath:service_config_prod.xml", "classpath:service_config_dev.xml"})
public class ServiceConfig {
}
