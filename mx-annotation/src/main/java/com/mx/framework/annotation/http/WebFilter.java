package com.mx.framework.annotation.http;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description 声明一个过滤器
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
public @interface WebFilter {
    /**
     * 过滤请求路径
     * @return
     */
    String requestUrl() default  "/*";

    /**
     * 过滤器名字
     * @return
     */
    String filterName() default "";
}
