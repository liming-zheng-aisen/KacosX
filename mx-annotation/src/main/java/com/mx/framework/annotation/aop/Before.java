package com.mx.framework.annotation.aop;

import java.lang.annotation.*;


/**
 * @Desc 前置通知
 * @Author Zheng.LiMing
 * @Date 2020/1/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
    String pointcup();
}
