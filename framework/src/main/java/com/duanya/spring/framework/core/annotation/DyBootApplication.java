package com.duanya.spring.framework.core.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DyComponent
@DyConfiguration
@DyScanner(packageNames = {})
public @interface DyBootApplication {

}
