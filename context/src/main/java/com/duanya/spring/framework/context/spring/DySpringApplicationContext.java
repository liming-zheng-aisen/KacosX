package com.duanya.spring.framework.context.spring;

import com.duanya.spring.framework.context.base.DyApplicationContext;
import com.duanya.spring.framework.context.exception.DyContextException;
import com.duanya.spring.framework.context.manager.DyContextManager;
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
public class DySpringApplicationContext implements DyApplicationContext {

    private static final Logger log = LoggerFactory.getLogger(DySpringApplicationContext.class);

    private static Map<String,Object> applicationContext;

    private DySpringApplicationContext(){
        applicationContext=new HashMap<>();
    }


    public void registeredBeanMap(Map<String ,Object> beanMap) throws DyContextException {
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

    private void hasKey(String key) throws DyContextException {
        if (applicationContext.containsKey(key)){
            throw new DyContextException(key+"名字重复出现，请重新检测！");
        }
    }

    @Override
    public Object getBean(String beanName, Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return applicationContext.get(beanName);
    }

    @Override
    public void registerBean(String beanName, Object object) throws DyContextException {
        applicationContext.put(beanName,object);
    }

    public static class Builder{

        private final static DySpringApplicationContext context=new DySpringApplicationContext();

        public static  DySpringApplicationContext getDySpringApplicationContext() {
            DyContextManager contextManager=DyContextManager.BuilderContext.getContextManager();
            if (!contextManager.hasContext(context)){
                contextManager.registerApplicationContext(context);
            }
            return context;
        }
    }
}
