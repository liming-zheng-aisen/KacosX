package com.macos.framework.context.base;

import com.macos.framework.context.exception.ContextException;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description 程序根上下
 */
@SuppressWarnings("all")
public interface ApplicationContextApi {


     Object getBean(String beanName,Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException;

     boolean registerBean(String beanName,Object object) throws ContextException;

     boolean registerBean(Object object) throws ContextException;

}
