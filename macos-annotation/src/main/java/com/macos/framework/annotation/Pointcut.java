package com.macos.framework.annotation;

import java.lang.annotation.*;

/**
 * @Desc 切入点
 * @Author Zheng.LiMing
 * @Date 2020/1/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Pointcut {
    String value();
}
