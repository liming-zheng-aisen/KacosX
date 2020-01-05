package com.macos.framework.annotation;

import java.lang.annotation.*;


/**
 * @Desc 前置通知
 * @Author Zheng.LiMing
 * @Date 2020/1/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Before {
    String value();
}
