package com.macos.framework.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description dyboot程序
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@AutoConfiguration
@MacosXScanner(packageNames = {})
public @interface MacosXApplication {

}
