package com.macos.framework.core.bean.definition;

import com.macos.framework.core.bean.factory.api.BeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aisen
 * @mail 1308404897@qq.com
 * @creater 2019/12/16 10:18:35
 * @desc bean的定义信息
 */
public class BeanDefinition {

    /**是否为prototype*/
    private volatile boolean prototype;

    /**父类信息*/
    private volatile List<Class> superClasses = new ArrayList<>();

    /**当前类信息*/
    private volatile Class target = null;

    /**bean的名字*/
    private volatile String beanName;

    /**当前bean的实例*/
    private volatile Object bean;

    private volatile BeanFactory factory;

    public boolean isPrototype(){
        return prototype;
    }
    public boolean isSington(){
        return !prototype;
    }
    public void setPrototype(){
        prototype = true;
    }
    public void setSingleton() {
        prototype = false;
    }

    public List<Class> getSuperClasses() {
        return superClasses;
    }

    public void setSuperClasses(List<Class> superClasses) {
        this.superClasses = superClasses;
    }

    public Class getTarget() {
        return target;
    }

    public void setTarget(Class target) {
        this.target = target;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
