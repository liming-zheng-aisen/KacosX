package com.mx.framework.annotation.http;

import com.mx.framework.annotation.core.Component;
import com.mx.framework.enums.ScopeType;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description  初始化，返回json数据
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Component
public @interface RestAPI {
    /**
     * 实例名称
     * @return
     */
    String value() default "";
    /**
     * 原型模式还是单例模式，默认单例
     * @return
     */
    ScopeType scope() default ScopeType.SINGLETON;
}
