package com.macos.framework.annotation;


import java.lang.annotation.*;

/**
 * @Desc 声明配置前缀
 * @Author Zheng.LiMing
 * @Date 2020/1/12
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PropertySource {
    String[] source();
}
