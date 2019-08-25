package com.duanya.spring.framework.core.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface DyAutowired {
    String value() default "";
}
