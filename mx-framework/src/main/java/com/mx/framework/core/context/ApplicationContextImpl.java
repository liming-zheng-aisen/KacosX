package com.mx.framework.core.context;

import com.mx.aop.factory.ProxyFacotry;
import com.mx.common.util.StringUtils;
import com.mx.framework.context.base.ApplicationContextApi;
import com.mx.framework.context.exception.ContextException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zheng.liming
 * @date 2019/8/20
 * @description
 */
@Slf4j
public class ApplicationContextImpl implements ApplicationContextApi {

    private static Map<String,Object> applicationContext;

    private ApplicationContextImpl(){
        applicationContext=new HashMap<>();
    }


    public void registeredBeanMap(Map<String ,Object> beanMap) throws ContextException {
        if (null==beanMap||beanMap.size()==0){
            return;
        }
        Iterator iterator= beanMap.keySet().iterator();
        while (iterator.hasNext()){
            String key=(String) iterator.next();
            hasKey(key);
            registerBean(key,beanMap.get(key));
        }
    }


    public Map<String,Object> getContext(){
        return applicationContext;
    }

    private void hasKey(String key) throws ContextException {
        if (applicationContext.containsKey(key)){
            throw new ContextException(key+"名字重复出现，请重新检测！");
        }
    }

    @Override
    public Object getBean(String beanName, Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (StringUtils.isEmptyPlus(beanName)){
            beanName = beanClass.getName();
        }
        return applicationContext.get(beanName);
    }

    @Override
    public boolean registerBean(String beanName , Object object) throws ContextException {
        if (StringUtils.isEmptyPlus(beanName)){
            beanName = object.getClass().getName();
        }
        applicationContext.put(beanName,object);
        return true;
    }

    @Override
    public Object registerBean(String beanName, Class object) throws ContextException {
        if (StringUtils.isEmptyPlus(beanName)){
            beanName = object.getName();
        }
        Object beanProxy = ProxyFacotry.getProxyInstance(object);
        applicationContext.put(beanName,beanProxy);
        return beanProxy;
    }

    public static class Builder{

        private final static ApplicationContextImpl context=new ApplicationContextImpl();

        public static ApplicationContextImpl getApplicationContext() {
            return context;
        }

    }
}
