package com.macos.framework.context.manager;

import com.macos.framework.context.base.ApplicationContextApi;

/**
 * @Desc 上下文管理器
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public  class ContextManager {

    private ContextBean contextBean;


    private ContextManager(){

    }

    public synchronized void registerApplicationContext(ApplicationContextApi context){
        if (context==null){
            return;
        }
        if (null==contextBean){
            contextBean=new ContextBean(context);
            return;
        }else {
            regiestNextContextBean(contextBean,context);
        }
    }

    private synchronized void regiestNextContextBean(ContextBean nextContextBean, ApplicationContextApi context){
        if (null==nextContextBean.getNextContextBean()){
            nextContextBean.setNextContextBean(new ContextBean(context));
            return;
        }else {
            regiestNextContextBean(nextContextBean.getNextContextBean(),context);
        }
    }

    public synchronized Boolean hasContext(ApplicationContextApi context){
        return foundContext(contextBean,context);
    }


    private synchronized Boolean foundContext(ContextBean contextBean, ApplicationContextApi target){

        if (contextBean==null){
            return false;
        }

        if (null!=contextBean.getCurrentContext()){
            if (contextBean.getCurrentContext()==target){
                return true;
            }
        }

        return foundContext(contextBean.getNextContextBean(),target);
    }

    public synchronized ApplicationContextApi getTargetContext(Class c){
        return foundContext(c,contextBean);
    }
    private synchronized ApplicationContextApi foundContext(Class c, ContextBean context){
        if (null==context||context.getCurrentContext()==null){
            return null;
        }
        if (context.getCurrentContext().getClass().equals(c)){
            return context.getCurrentContext();
        }else {
            return foundContext(c,context);
        }
    }

    public synchronized Object getBean(String beanName,Class beanClass) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
       return getBean(contextBean,beanName,beanClass);
    }

    private synchronized Object getBean(ContextBean contextBean, String beanName, Class beanClass) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (null==contextBean){
            return null;
        }
        Object object=contextBean.getCurrentContext().getBean(beanName,beanClass);
        if (null!=object) {
            return object;
        }else {
            return getBean(contextBean.getNextContextBean(), beanName, beanClass);
        }
    }


    public static class BuilderContext{

        private final static ContextManager contextManager = new ContextManager();

        public static ContextManager getContextManager(){
            return contextManager;
        }
    }

}
