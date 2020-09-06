package com.mx.framework.mvc.handler.bean;

import com.mx.framework.enums.HttpMethod;

import java.lang.reflect.Method;

/**
 * @author zheng.liming
 * @date 2019/8/21
 * @description
 */
public class RequestUrlBean {

    /**
     * 处理的类
     */
    private Class handler;

    /**
     * 请求路径
     */
    private String[] requestUrl;

    /**
     * 关联的方法
     */
    private Method handlerMethod;

    /**
     * 关联的方法
     */
    private HttpMethod httpMethod;

    /**
     * 参数名
     */
    private boolean pathRequest;


    public Class getHandler() {
        return handler;
    }

    public void setHandler(Class handler) {
        this.handler = handler;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }

    public void setHandlerMethod(Method handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public boolean isPathRequest() {
        return pathRequest;
    }

    public void setPathRequest(boolean pathRequest) {
        this.pathRequest = pathRequest;
    }

    public String[] getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String[] requestUrl) {
        this.requestUrl = requestUrl;
    }
}
