package com.mx.framework.annotation.aop;

import java.lang.annotation.*;

/**
 * @Desc 切入点
 * @Author Zheng.LiMing
 * @Date 2020/1/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Pointcut {
    String value();
}
