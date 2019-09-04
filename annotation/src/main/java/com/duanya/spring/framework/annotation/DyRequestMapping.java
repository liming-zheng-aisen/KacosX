package com.duanya.spring.framework.annotation;

import com.duanya.spring.framework.mvc.enums.DyMethod;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface DyRequestMapping {
    /**请求路径*/
    String value() default "/";
    /**请求方法*/
    DyMethod method() default DyMethod.GET;
    /**接口说明*/
    String doc() default "";
}
