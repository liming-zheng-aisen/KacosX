package com.mx.framework.annotation.core;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 依赖注入
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
public @interface Autowired {
    /**
     * bean的名字
     * @return
     */
    String value() default "";
}
