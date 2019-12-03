package com.macos.framework.annotation;

import java.lang.annotation.*;


/**
 * @Desc dyboot组件启动器
 * @Author Zheng.LiMing
 * @Date 2019/8/29
 */

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MacosApplicationStarter {
    String[] scannerPath();
    int order();
}
