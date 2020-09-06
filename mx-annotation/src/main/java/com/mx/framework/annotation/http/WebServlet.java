package com.mx.framework.annotation.http;

import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/9/10
 * @description 声明一个servlet
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
public @interface WebServlet {
    /**
     * 处理的请求url
     * @return
     */
    String url();
}
