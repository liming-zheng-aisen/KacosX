package com.macos.framework.annotation;

import com.macos.framework.enums.ScopeType;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 实例化一个bean
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Component
public @interface Service {
    /**
     * bean的名字
     * @return
     */
    String value() default "";
    /**
     * 原型模式还是单例模式，默认单例
     * @return
     */
    ScopeType scope() default ScopeType.SINGLETON;
}
