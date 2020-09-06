package com.mx.framework.annotation.http;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/21
 * @description 路径参数
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface PathVariable {
    String value() default "";
    /**参数说明*/
    String dom() default "";
}
