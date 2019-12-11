package com.macos.framework.core.load.abs;

/**
 * @author zheng.liming
 * @date 2019/8/27
 * @description
 */
public abstract class BeanLoad {

    protected BeanLoad nextLoader;

    public abstract void load(Class c) throws Exception;

    public BeanLoad getNextLoader() {
        return nextLoader;
    }

    public void setNextLoader(BeanLoad nextLoader) {
        this.nextLoader = nextLoader;
    }
}
