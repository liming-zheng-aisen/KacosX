package com.macos.framework.context.manager;

import com.macos.framework.context.base.ApplicationContextApi;

/**
 * @Desc DyContextBean
 * @Author Zheng.LiMing
 * @Date 2019/9/8
 */
public class ContextBean {
    private ApplicationContextApi currentContext;
    private ContextBean nextContextBean;

    public ApplicationContextApi getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(ApplicationContextApi currentContext) {
        this.currentContext = currentContext;
    }

    public ContextBean getNextContextBean() {
        return nextContextBean;
    }

    public void setNextContextBean(ContextBean nextContextBean) {
        this.nextContextBean = nextContextBean;
    }

    public ContextBean(ApplicationContextApi currentContext) {
        this.currentContext = currentContext;
    }
}
