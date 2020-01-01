package com.macos.framework.core.bean.util;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Component;
import com.macos.framework.annotation.RestAPI;
import com.macos.framework.annotation.Service;
import com.macos.framework.core.bean.definition.BeanDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc 专门转换bean定义
 * @Author Zheng.LiMing
 * @Date 2019/12/30
 */
public class BeanDefinitionUtil {

    private BeanDefinitionUtil(){

    }

    private static BeanDefinitionUtil beanDefinitionUtil= new BeanDefinitionUtil();

    /**
     * 将class信息解析到BeanDefinition中
     * @param c
     * @return
     */
    public static BeanDefinition convertToBeanDefinition(Class c){
        if (c==null){
            return null;
        }
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinitionUtil.start(c,beanDefinition)
                .initPrototypeAndBeanName(c,beanDefinition)
                .initSuperClass(c,beanDefinition);
        return beanDefinition;
    }

    /**
     * 开始解析
     * @param c
     * @param beanDefinition
     * @return
     */
    private BeanDefinitionUtil start(Class c,BeanDefinition beanDefinition){
        beanDefinition.setTarget(c);
        return this;
    }

    /**
     * 实现接口
     * @param c
     * @param classList
     */
    private static void setInterfaces(Class c,List<Class> classList){
        if (c==Object.class){
            return ;
        }
        Class[] interfaces = c.getInterfaces();
        if (interfaces!=null && interfaces.length>0){
            for (Class i: interfaces){
                classList.add(i);
                setInterfaces(i,classList);
            }
        }
    }

    /**
     * 实现class对象
     * @param c
     * @param classList
     */
    private static void setSuperClass(Class c,List<Class> classList){

        if (c==Object.class){
            return ;
        }

        classList.add(c);

        Class superclass = c.getSuperclass();

        if (superclass==Object.class){
            return;
        }

        setSuperClass(superclass,classList);
    }

    /**
     * 初始化接口
     * @param c
     * @param beanDefinition
     * @return
     */
    private BeanDefinitionUtil  initSuperClass(Class c,BeanDefinition beanDefinition){
        List<Class> classList = new ArrayList<>();
        setInterfaces(c,classList);
        setSuperClass(c,classList);
        beanDefinition.setSuperClasses(classList);
        return this;
    }

    /**
     * 设置bean名字和实例类型
     * @param c
     * @param beanDefinition
     * @return
     */
    private BeanDefinitionUtil initPrototypeAndBeanName(Class c,BeanDefinition beanDefinition){

        if (c.isAnnotationPresent(Component.class)){
            Component component = (Component)c.getAnnotation(Component.class);
            setPrototypeAndBeanName(c,component.scope().isPrototype(),component.value(),beanDefinition);
            return this;
        }

        if (c.isAnnotationPresent(RestAPI.class)){
            RestAPI component = (RestAPI)c.getAnnotation(RestAPI.class);
            setPrototypeAndBeanName(c,component.scope().isPrototype(),component.value(),beanDefinition);
            return this;
        }

        if (c.isAnnotationPresent(Service.class)){
            Service component = (Service)c.getAnnotation(Service.class);
            setPrototypeAndBeanName(c,component.scope().isPrototype(),component.value(),beanDefinition);
            return this;
        }

        return this;
    }

    /**
     * 设置bean名字和实例类型
     * @param c
     * @param isPrototype
     * @param beanName
     * @param beanDefinition
     */
    private void setPrototypeAndBeanName(Class c,boolean isPrototype,String beanName,BeanDefinition beanDefinition){
       if (StringUtils.isEmpty(beanName)){
           beanName =  StringUtils.toLowerCaseFirstName(c.getSimpleName());
       }
       beanDefinition.setBeanName(beanName);
        if (isPrototype){
            beanDefinition.setPrototype();
        }else {
            beanDefinition.setSingleton();
        }
    }

}
