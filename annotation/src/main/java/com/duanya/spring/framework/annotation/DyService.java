package com.duanya.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 实例化一个bean
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@DyComponent
public @interface DyService {
    String value() default "";
}