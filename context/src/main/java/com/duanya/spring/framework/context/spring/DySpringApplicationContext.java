package com.duanya.spring.framework.context.spring;

import com.duanya.spring.framework.context.base.DyApplicationContext;
import com.duanya.spring.framework.context.exception.DyContextException;
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
public class DySpringApplicationContext extends DyApplicationContext {

    private static final Logger log = LoggerFactory.getLogger(DySpringApplicationContext.class);

    public DySpringApplicationContext(){
        if (null==applicationContext) {
            applicationContext = new HashMap<String, Object>();
        }
    }

    public void registeredBean(String key,Object bean) throws DyContextException { hasKey(key);
        applicationContext.put(key,bean);
    }

    public void registeredBeanMap(Map<String ,Object> beanMap) throws DyContextException {
        if (null==beanMap||beanMap.size()==0){
            return;
        }
        Iterator iterator= beanMap.keySet().iterator();
        while (iterator.hasNext()){
            String key=(String) iterator.next();
            hasKey(key);
            registeredBean(key,beanMap.get(key));
        }
    }

    @Override
    public Object getBean(String key) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Object bean = super.getBean(key);

        return bean;
    }

    public Map<String,Object> getContext(){
        return applicationContext;
    }

    public void hasKey(String key) throws DyContextException {
        if (applicationContext.containsKey(key)){
            log.error(key+"名字重复出现，请重新检测！");
            throw new DyContextException(key+"名字重复出现，请重新检测！");
        }
    }
}
