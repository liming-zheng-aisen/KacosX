package com.duanya.spring.framework.core.bean.factory;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyAutowired;
import com.duanya.spring.framework.context.manager.DyContextManager;

import java.lang.reflect.Field;

/**
 * @Desc DyAutowiredFactory
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyAutowiredFactory {

    public static void doAutowired(Object bean) throws Exception {
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
                    throw new Exception(beanClass.getName().toString()+"为空！");
                }
                f.set(bean,fBean);

            }
        }
    }
}
