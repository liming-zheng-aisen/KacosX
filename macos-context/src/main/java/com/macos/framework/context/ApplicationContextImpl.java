package com.macos.framework.context;

import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.exception.ContextException;
import com.macos.framework.context.manager.ContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zheng.liming
 * @date 2019/8/20
 * @description
 */
public class ApplicationContextImpl implements ApplicationContextApi {

    private static final Logger log = LoggerFactory.getLogger(ApplicationContextImpl.class);

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
        return applicationContext.get(beanName);
    }

    @Override
    public void registerBean(String beanName, Object object) throws ContextException {
        applicationContext.put(beanName,object);
    }

    public static class Builder{

        private final static ApplicationContextImpl context=new ApplicationContextImpl();

        public static ApplicationContextImpl getDySpringApplicationContext() {
            ContextManager contextManager= ContextManager.BuilderContext.getContextManager();
            if (!contextManager.hasContext(context)){
                contextManager.registerApplicationContext(context);
            }
            return context;
        }
    }
}
