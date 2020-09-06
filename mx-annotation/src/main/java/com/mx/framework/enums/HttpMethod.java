package com.mx.framework.enums;

/**
 *
 * 这里列举了，HTTP常用的几种请求方式
 * @author zheng.liming
 * @date 2019/8/5
 * @description 请求的方法
 */
@SuppressWarnings("all")
public enum HttpMethod {
    GET("GET","用于访问资源"),
    POST("POST","用于执行业务的请求"),
    PUT("PUT","用于资源的更改or新增"),
    DELETE("DELETE","删除资源"),
    HEAD("HEAD","检查资源的有效性"),
    OPTIONS("OPTIONS","用于试探请求，跨域较多"),
    TRACE("TRACE","HTTP（超文本传输）协议定义的一种协议调试方法");

    /**
     * 请求方法
     */
    private String method;

    /**
     * 说明文档
     */
    private String dom;

    HttpMethod(String method, String dom) {
        this.method = method;
        this.dom = dom;
    }
}
