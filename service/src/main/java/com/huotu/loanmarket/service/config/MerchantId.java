package com.huotu.loanmarket.service.config;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by hot on 2017/11/22.
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MerchantId {
    int value() default 0;
}
