package com.macos.aop.core.even.bean;

import lombok.Data;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Desc 触发事件的对象信息，作为事件回调参数
 * @Author Zheng.LiMing
 * @Date 2020/1/16
 */
@Data
public class EvenBeanReturn extends EvenBeanInfo{

    protected MethodProxy methodProxy;

    public EvenBeanReturn(Object bean,  Method method,Object[] args,MethodProxy methodProxy) {
        this.bean = bean;
        this.args = args;
        this.method = method;
        this.methodProxy = methodProxy;
    }

    public Object invoke() throws Throwable {
        setInvoke(true);
        return methodProxy.invokeSuper(bean,args);
    }
}
