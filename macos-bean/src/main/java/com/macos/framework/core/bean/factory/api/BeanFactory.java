package com.macos.framework.core.bean.factory.api;

/**
 * @Desc BeanFactory
 * @Author Zheng.LiMing
 * @Date 2019/12/29
 */
public interface BeanFactory {

    /**
     * 获取一个实例
     * @param beanName
     * @param beanClass
     * @return
     */
    Object getBean(String beanName,Class beanClass)throws Exception;

    /**
     * 注册一个bean
     * @param beanName
     * @param beanClass
     */
    boolean registerBean(String beanName,Class beanClass)throws Exception;
}
