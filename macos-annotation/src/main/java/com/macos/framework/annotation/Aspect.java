package com.macos.framework.annotation;

import java.lang.annotation.*;

/**
 * @Desc 切面
 * @Author Zheng.LiMing
 * @Date 2020/1/4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Aspect {
    String value();
}
