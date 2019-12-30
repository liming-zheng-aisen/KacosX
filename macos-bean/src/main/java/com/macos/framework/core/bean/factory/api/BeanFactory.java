package com.macos.framework.core.bean.factory.api;

/**
 * @Desc BeanFactory
 * @Author Zheng.LiMing
 * @Date 2019/12/29
 */
public interface BeanFactory {

    Object getBean(String beanName,Class beanClass);

    void registerBean(String beanName,Class beanClass);
}
