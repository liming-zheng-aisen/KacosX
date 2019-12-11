package com.macos.framework.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description 扫描器
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MacosXScanner {
    /**
     * 包路径
     * @return
     */
    String[] packageNames();
}
