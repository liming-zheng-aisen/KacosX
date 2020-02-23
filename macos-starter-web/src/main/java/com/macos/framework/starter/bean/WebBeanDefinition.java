package com.macos.framework.starter.bean;

import com.macos.framework.core.bean.definition.BeanDefinition;

/**
 * @Desc WebBeanDefin
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class WebBeanDefinition extends BeanDefinition {

    protected Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
