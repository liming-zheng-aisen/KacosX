package com.mx.framework.annotation.boot;

import java.lang.annotation.*;


/**
 * @Desc boot组件启动器
 * @Author Zheng.LiMing
 * @Date 2019/8/29
 */

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MacosXApplicationStarter {
    String[] scannerPath();
    int order();
}
