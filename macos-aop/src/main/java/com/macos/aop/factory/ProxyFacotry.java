package com.macos.aop.factory;

import com.macos.aop.proxy.BeanProxy;
import net.sf.cglib.proxy.Enhancer;

/**
 * @Desc EnhancerFacotry
 * @Author Zheng.LiMing
 * @Date 2020/2/23
 */
public class ProxyFacotry {

    //给目标对象创建一个代理对象
    public static Object getProxyInstance(Class target) {
        //工具类
        Enhancer en = new Enhancer();
        //设置父类
        en.setSuperclass(target);
        //设置回调函数
        en.setCallback(new BeanProxy());
        //创建子类代理对象
        return en.create();
    }
}
