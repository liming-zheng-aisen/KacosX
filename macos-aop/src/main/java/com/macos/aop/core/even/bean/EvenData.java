package com.macos.aop.core.even.bean;

import java.lang.reflect.Method;

/**
 * @Desc EvenData
 * @Author Zheng.LiMing
 * @Date 2020/2/2
 */
public class EvenData extends EvenBeanInfo{
    private Object data;

    public EvenData(Object bean, Method method, Object[] args, Object data) {
        super(bean, method, args);
        this.data = data;
    }

    public EvenData() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
