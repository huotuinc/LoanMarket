package com.huotu.loanmarket.service.model.tongdun;

import java.lang.annotation.*;

/**
 * 同盾字段注解
 * @author wm
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TongdunField {
    /**
     * 名称
     * @return
     */
    String name() default "";
}