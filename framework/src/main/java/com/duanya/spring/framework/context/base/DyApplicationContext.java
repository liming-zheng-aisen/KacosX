package com.duanya.spring.framework.context.base;

import com.duanya.spring.framework.context.exception.DyContextException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description 程序根上下
 */
public class DyApplicationContext{

    /**上下文Bean管理*/
    protected static Map<String,Object> applicationContext = new HashMap<String, Object>();

    public Object getBean(String beanName) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (applicationContext.containsKey(beanName)){
           return applicationContext.get(beanName);
        }
        return null;
    }

    public  void setContextBean(String beanName,Object object) throws DyContextException {
        if (applicationContext.containsKey(beanName)){
            throw new DyContextException("上下文已经存在beanName为"+beanName+"的对象");
        }
        applicationContext.put(beanName,object);
    }

}
