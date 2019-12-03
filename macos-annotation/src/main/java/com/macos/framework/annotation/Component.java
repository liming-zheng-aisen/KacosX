package com.macos.framework.annotation;


import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 实例化一个bean
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
public @interface Component {
    /**
     * 实例名字
     * @return
     */
    String value() default "";
}
