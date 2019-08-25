package com.duanya.spring.framework.core.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/16
 * @description
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DyValue {
    String value();
}
