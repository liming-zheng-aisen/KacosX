package com.mx.framework.annotation.core;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/16
 * @description 注入配置文件属性值
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    /**
     * 属性表达式，${配置文件的key}:默认值
     * @return
     */
    String value();
}
