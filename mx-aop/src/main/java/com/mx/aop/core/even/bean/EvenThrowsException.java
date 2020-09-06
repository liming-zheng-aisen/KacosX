package com.mx.aop.core.even.bean;

import java.lang.reflect.Method;

/**
 * @Desc 异常信息
 * @Author Zheng.LiMing
 * @Date 2020/2/2
 */
public class EvenThrowsException extends EvenBeanInfo {

    private Exception exception;

    public EvenThrowsException(Object bean, Method method, Object[] args, Exception exception) {
        super(bean, method, args);
        this.exception = exception;
    }

    public EvenThrowsException() {
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
