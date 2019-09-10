package com.duanya.spring.framework.context.manager;

import com.duanya.spring.framework.context.base.DyApplicationContext;

/**
 * @Desc 上下文管理器
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public  class DyContextManager {

    private  DyContextBean contextBean;


    private DyContextManager(){

    }

    public synchronized void registerApplicationContext(DyApplicationContext context){
        if (context==null){
            return;
        }
        if (null==contextBean){
            contextBean=new DyContextBean(context);
            return;
        }else {
            regiestNextContextBean(contextBean,context);
        }
    }

    private synchronized void regiestNextContextBean(DyContextBean nextContextBean,DyApplicationContext context){
        if (null==nextContextBean.getNextContextBean()){
            nextContextBean.setNextContextBean(new DyContextBean(context));
            return;
        }else {
            regiestNextContextBean(nextContextBean.getNextContextBean(),context);
        }
    }

    public synchronized Boolean hasContext(DyApplicationContext context){
        return foundContext(contextBean,context);
    }


    private synchronized Boolean foundContext(DyContextBean contextBean,DyApplicationContext target){

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

    public synchronized DyApplicationContext getTargetContext(Class c){
        return foundContext(c,contextBean);
    }
    private synchronized  DyApplicationContext foundContext(Class c,DyContextBean context){
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

    private synchronized Object getBean(DyContextBean contextBean,String beanName,Class beanClass) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
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

        private final static DyContextManager contextManager = new DyContextManager();

        public static DyContextManager getContextManager(){
            return contextManager;
        }
    }

}
