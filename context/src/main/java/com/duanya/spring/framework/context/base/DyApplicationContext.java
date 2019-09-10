package com.duanya.spring.framework.context.base;

import com.duanya.spring.framework.context.exception.DyContextException;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description 程序根上下
 */
@SuppressWarnings("all")
public interface DyApplicationContext{


    public Object getBean(String beanName,Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException;

    public  void registerBean(String beanName,Object object) throws DyContextException ;


}
