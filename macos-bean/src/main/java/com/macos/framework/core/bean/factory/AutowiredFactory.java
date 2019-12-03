package com.macos.framework.core.bean.factory;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Autowired;
import com.macos.framework.annotation.RestAPI;
import com.macos.framework.context.manager.ContextManager;


import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * @Desc 自动注入工厂
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class AutowiredFactory {

    public static void doAutowired(Object bean, Properties evn) throws Exception {
        if (null==bean){
            return;
        }
        ContextManager contextManager= ContextManager.BuilderContext.getContextManager();
        Field[] fields= bean.getClass().getDeclaredFields();
        for (Field f:fields){
            if (f.isAnnotationPresent(Autowired.class)){
                f.setAccessible(true);
                Autowired dyAutowired=f.getAnnotation(Autowired.class);
                String beanName=dyAutowired.value();
                Class beanClass=null;
                if (StringUtils.isEmptyPlus(beanName)){
                    String fName=f.getType().getCanonicalName();
                    beanClass= Class.forName(fName);
                    beanName=StringUtils.toLowerCaseFirstName(beanClass.getSimpleName());
                }
                Object fBean=contextManager.getBean(beanName,beanClass);
                if (fBean==null){
                    if (f.getType().isAnnotationPresent(RestAPI.class)){
                        fBean= BeanFactory.initNewBean(f.getType().getName(),evn);
                    }else {
                        throw new Exception(beanClass.getName().toString() + "为空！");
                    }
                }
                Class[] classes=f.getType().getInterfaces();
                Class[] classes1=new Class[classes.length+1];
                System.arraycopy(classes,0,classes1,0,classes.length);
                classes1[classes1.length-1]=f.getType();
                BeanProxy beanProxy =new BeanProxy(fBean);
                Object object = Proxy.newProxyInstance(f.getType().getClassLoader(),classes1, beanProxy);
                f.set(bean,object);

            }
        }
    }
}
