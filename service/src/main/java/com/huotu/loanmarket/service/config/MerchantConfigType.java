package com.huotu.loanmarket.service.config;

/**
 * @author wm
 */

import com.huotu.loanmarket.service.enums.MerchantConfigEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 商家配置类型注解
 * @author wm
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MerchantConfigType {
    /**
     * 所属
     * @return
     */
    MerchantConfigEnum type() default MerchantConfigEnum.SESAME;
}