package com.macos.framework.core.bean.definition;

import com.macos.framework.context.base.ApplicationContextApi;

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

    /**字段前缀仅对于导入配置有效*/
    private volatile String prefix="";


    /**指定上下文管理，允许替换*/
    private volatile ApplicationContextApi contextApi;

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

    public void setContextApi(ApplicationContextApi contextApi) {
        this.contextApi = contextApi;
    }


    public ApplicationContextApi getContextApi(){
        return this.contextApi;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
