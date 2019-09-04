package com.duanya.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DyAutoConfiguration
@DyScanner(packageNames = {})
public @interface DyBootApplication {

}
