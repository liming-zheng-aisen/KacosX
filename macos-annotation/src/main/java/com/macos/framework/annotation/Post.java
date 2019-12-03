package com.macos.framework.annotation;;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/21
 * @description post请求
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Post {
    /**请求路径*/
    String value() default "/";
    /**接口说明*/
    String doc() default "";
}
