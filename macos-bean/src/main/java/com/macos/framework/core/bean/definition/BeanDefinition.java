package com.macos.framework.core.bean.definition;

import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.exception.ContextException;
import com.macos.framework.core.bean.definition.work.WorkEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aisen
 * @mail 1308404897@qq.com
 * @creater 2019/12/16 10:18:35
 * @desc bean的定义信息
 */
public class BeanDefinition implements ApplicationContextApi{


    /**是否为prototype*/
    private volatile boolean prototype ;

    /**父类信息*/
    private volatile List<Class> superClasses = new ArrayList<>() ;

    /**当前类信息*/
    private volatile Class target = null ;

    /**bean的名字*/
    private volatile String beanName = null ;

    /**字段前缀仅对于导入配置有效*/
    private volatile String prefix = "" ;

    /**工作环境，默认应用程序*/
    private volatile WorkEnum work = WorkEnum.Apllication ;

    /**
     * 请求根路径
     */
    private volatile String requestPath = "/" ;


    /**指定上下文管理，允许替换*/
    private volatile ApplicationContextApi contextApi ;

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

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
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

    public WorkEnum getWork() {
        return work;
    }

    public void setWork(WorkEnum work) {
        this.work = work;
    }

    @Override
    public Object getBean(String beanName, Class beanClass) throws Exception {
       if (contextApi == null){
           return null;
       }

      return contextApi.getBean(beanName,beanClass);
    }

    @Override
    public boolean registerBean(String beanName, Object object) throws ContextException {
        if (contextApi == null){
            return false;
        }
        return contextApi.registerBean(beanName,object);
    }

    @Override
    public Object registerBean(String beanName, Class object) throws ContextException {
        if (contextApi == null){
            return false;
        }
        return contextApi.registerBean(beanName,object);
    }
}
