package com.mx.aop.core.even.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 * @Desc 触发事件的对象信息，作为事件回调参数
 * @Author Zheng.LiMing
 * @Date 2020/1/17
 */
public class EvenBeanInfo {

    /**
     * 执行对象
     */
    protected Object bean;
    /**
     * 执行的方法
     */
    protected Method method;
    /**
     * 方法的参数
     */
    protected Object[] args;



    /**
     * 是否已经执行
     */
    protected boolean isInvoke;

    public EvenBeanInfo(Object bean, Method method, Object[] args) {
        this.bean = bean;
        this.method = method;
        this.args = args;
    }

    public EvenBeanInfo() {
    }

    public Object getTarget(){
        return bean;
    }

    public String getBeanClassPackage(){
        return bean.getClass().getName();
    }

    public String getMethodName(){
        return method.getName();
    }

    public String getMatches(){
        return Modifier.toString(method.getModifiers())+" "+method.getReturnType().getName()+ " " + getBeanClassPackage()+"."+getMethodName()+"()";
    }

    public Object[] getArgs(){
        return args;
    }

    public Parameter[] getMethodParameter(){
        return method.getParameters();
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setArgs(Object[] orgs) {
        this.args = orgs;
    }

    public boolean isInvoke() {
        return isInvoke;
    }

    public void setInvoke(boolean invoke) {
        isInvoke = invoke;
    }
}
