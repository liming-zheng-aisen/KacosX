package com.duanya.spring.framework.core.bean.factory;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyAutowired;
import com.duanya.spring.framework.context.spring.DySpringApplicationContext;

import java.lang.reflect.Field;

/**
 * @Desc DyAutowiredFactory
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyAutowiredFactory {

    public static void doAutowired(Object bean) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (null==bean){
            return;
        }
        DySpringApplicationContext context=new DySpringApplicationContext();
        Field[] fields= bean.getClass().getDeclaredFields();
        for (Field f:fields){
            if (f.isAnnotationPresent(DyAutowired.class)){
                f.setAccessible(true);
                DyAutowired dyAutowired=f.getAnnotation(DyAutowired.class);
                String beanName=dyAutowired.value();
                if (StringUtils.isEmptyPlus(beanName)){
                    String fName=f.getType().getCanonicalName();
                    Class c= Class.forName(fName);
                    beanName=StringUtils.toLowerCaseFirstName(c.getSimpleName());
                }
                Object fBean=context.getBean(beanName);
                f.set(bean,fBean);

            }
        }
    }
}
