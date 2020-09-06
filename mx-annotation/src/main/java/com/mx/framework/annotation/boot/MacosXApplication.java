package com.mx.framework.annotation.boot;

import com.mx.framework.annotation.core.AutoConfiguration;
import com.mx.framework.annotation.core.Configuration;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description boot程序
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@AutoConfiguration
@Configuration
@MacosXScanner(packageNames = {})
public @interface MacosXApplication {

}
