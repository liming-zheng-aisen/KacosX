package com.duanya.spring.framework.mvc.handler.bean;

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
     * 关联的方法
     */
    private Method method;

    /**
     * url路径末尾是否是参数
     */
    private boolean isBringParam;
    /**
     * 参数名
     */
    private String paramName;


    public Class getHandler() {
        return handler;
    }

    public void setHandler(Class handler) {
        this.handler = handler;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public boolean isBringParam() {
        return isBringParam;
    }

    public void setBringParam(boolean bringParam) {
        isBringParam = bringParam;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

}
