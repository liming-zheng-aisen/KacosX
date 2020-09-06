package com.mx.framework.context.base;

import com.mx.framework.context.exception.ContextException;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description 程序根上下
 */
@SuppressWarnings("all")
public interface ApplicationContextApi {

     Object getBean(String beanName,Class beanClass) throws Exception;

     boolean registerBean(String beanName,Object object) throws ContextException;

     Object registerBean(String beanName,Class object) throws ContextException;

}
