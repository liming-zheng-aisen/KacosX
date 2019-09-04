package com.duanya.spring.framework.core.load;

/**
 * @author zheng.liming
 * @date 2019/8/27
 * @description
 */
public abstract class DyBeanLoad {

    protected DyBeanLoad nextLoader;

    public abstract void load(Class c) throws Exception;

    public DyBeanLoad getNextLoader() {
        return nextLoader;
    }

    public void setNextLoader(DyBeanLoad nextLoader) {
        this.nextLoader = nextLoader;
    }
}
