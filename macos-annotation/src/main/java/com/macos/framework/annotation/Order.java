package com.macos.framework.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description 过滤器的排序
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
public @interface Order {
    /**
     * 值越小越靠前
     * @return
     */
    int value() ;
}
