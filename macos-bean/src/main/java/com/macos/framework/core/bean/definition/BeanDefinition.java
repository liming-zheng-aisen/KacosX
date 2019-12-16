package com.macos.framework.core.bean.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aisen
 * @mail 1308404897@qq.com
 * @creater 2019/12/16 10:18:35
 * @desc bean的定义信息
 */
public class BeanDefinition {
    private volatile boolean prototype;
    private volatile List<Class> superClasses = new ArrayList<>();
    private volatile Class target = null;
    private volatile String beanName;

    public boolean isPrototype(){
        return prototype;
    }
    public boolean isSington(){
        return !prototype;
    }
    public void setPrototype(){
        prototype = true;
    }
    public void setSington() {
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
}
