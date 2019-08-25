package com.duanya.spring.framework.core.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
public @interface DyWebFilter {
    String requestUrl() default  "/*";
    String filterName() default "";
}
