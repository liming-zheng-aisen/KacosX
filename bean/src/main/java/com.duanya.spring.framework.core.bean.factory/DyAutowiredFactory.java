package com.duanya.spring.framework.core.bean.factory;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyAutowired;
import com.duanya.spring.framework.annotation.DyRestController;
import com.duanya.spring.framework.context.manager.DyContextManager;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * @Desc 自动注入工厂
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyAutowiredFactory {

    public static void doAutowired(Object bean, Properties evn) throws Exception {
        if (null==bean){
            return;
        }
        DyContextManager contextManager=DyContextManager.BuilderContext.getContextManager();
        Field[] fields= bean.getClass().getDeclaredFields();
        for (Field f:fields){
            if (f.isAnnotationPresent(DyAutowired.class)){
                f.setAccessible(true);
                DyAutowired dyAutowired=f.getAnnotation(DyAutowired.class);
                String beanName=dyAutowired.value();
                Class beanClass=null;
                if (StringUtils.isEmptyPlus(beanName)){
                    String fName=f.getType().getCanonicalName();
                    beanClass= Class.forName(fName);
                    beanName=StringUtils.toLowerCaseFirstName(beanClass.getSimpleName());
                }
                Object fBean=contextManager.getBean(beanName,beanClass);
                if (fBean==null){
                    if (f.getType().isAnnotationPresent(DyRestController.class)){
                        fBean=DyBeanFactory.initNewBean(f.getType().getName(),evn);
                    }else {
                        throw new Exception(beanClass.getName().toString() + "为空！");
                    }
                }
                Class[] classes=f.getType().getInterfaces();
                Class[] classes1=new Class[classes.length+1];
                System.arraycopy(classes,0,classes1,0,classes.length);
                classes1[classes1.length-1]=f.getType();
                DyBeanProxy dyBeanProxy=new DyBeanProxy(fBean);
                Object object = Proxy.newProxyInstance(f.getType().getClassLoader(),classes1,dyBeanProxy);
                f.set(bean,object);

            }
        }
    }
}
