package com.duanya.spring.framework.core.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/21
 * @description
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DyPost {
    String value() default "/";
}
