package com.macos.framework.core.bean.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Desc 实例化对象代理
 * @Author Zheng.LiMing
 * @Date 2019/9/15
 */
public class BeanProxy<T> implements InvocationHandler {

    T target;

    public BeanProxy(T bean){
            target=bean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object data = method.invoke(target,args);
        return data;
    }
}
