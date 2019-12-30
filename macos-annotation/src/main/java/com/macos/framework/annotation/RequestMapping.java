package com.macos.framework.annotation;


import com.macos.framework.enums.HttpMethod;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 自定义请求
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface RequestMapping {
    /**请求路径*/
    String value() default "/";
    /**请求方法*/
    HttpMethod method() default HttpMethod.GET;
    /**接口说明*/
    String doc() default "";
}
