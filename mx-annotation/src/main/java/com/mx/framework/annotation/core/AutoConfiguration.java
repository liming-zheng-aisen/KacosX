package com.mx.framework.annotation.core;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/26
 * @description 自动配置
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoConfiguration {
}
