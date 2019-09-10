package com.duanya.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/9/10
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
public @interface DyWebServlet {
    String url();
}
