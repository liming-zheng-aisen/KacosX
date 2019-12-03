package com.macos.framework.context.base;

import com.macos.framework.context.exception.ContextException;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description 程序根上下
 */
@SuppressWarnings("all")
public interface ApplicationContextApi {


    public Object getBean(String beanName,Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException;

    public  void registerBean(String beanName,Object object) throws ContextException;


}
