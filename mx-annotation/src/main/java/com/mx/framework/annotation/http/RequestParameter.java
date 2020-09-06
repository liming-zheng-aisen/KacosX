package com.mx.framework.annotation.http;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/21
 * @description 参数说明
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RequestParameter {

    /**
     * 字段名称
     * @return
     */
    String value();

    /**
     * 默认值
     * @return
     */
    String defaultValue() default "";

    /**
     * 是否必须的，默认必须的
     * @return
     */
    boolean request() default true;
    /**
     * 说明，暂时没有意义，预留字段
     * @return
     */
    String dom() default "";

}
