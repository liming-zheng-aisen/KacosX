package com.duanya.spring.framework.context.manager;

import com.duanya.spring.framework.context.base.DyApplicationContext;

/**
 * @Desc DyContextBean
 * @Author Zheng.LiMing
 * @Date 2019/9/8
 */
public class DyContextBean {
    private DyApplicationContext currentContext;
    private DyContextBean nextContextBean;

    public DyApplicationContext getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(DyApplicationContext currentContext) {
        this.currentContext = currentContext;
    }

    public DyContextBean getNextContextBean() {
        return nextContextBean;
    }

    public void setNextContextBean(DyContextBean nextContextBean) {
        this.nextContextBean = nextContextBean;
    }

    public DyContextBean(DyApplicationContext currentContext) {
        this.currentContext = currentContext;
    }
}
