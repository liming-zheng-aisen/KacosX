package com.macos.framework.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description 声明一个配置类，类似一个配置文件
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Component
public @interface Configuration {
    /**
     * 实例名字
     * @return
     */
    String value() default "";
}
