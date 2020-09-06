package com.mx.core.combiner.bese;

import com.mx.framework.context.base.ApplicationContextApi;
import com.mx.framework.context.exception.ContextException;
import com.mx.framework.core.context.ApplicationContextImpl;
import com.mx.framework.core.handle.base.BaseHandler;

/**
 * @Desc 基础组装器
 * @Author Zheng.LiMing
 * @Date 2020/2/19
 */
public abstract class BaseCombiner {

    protected BaseHandler handler;

    private static final ApplicationContextApi applicationContextApi = ApplicationContextImpl.Builder.getApplicationContext();

    public BaseCombiner inint(BaseHandler baseHandler){
        if (handler == null){
            handler = baseHandler;
        }
        return this;
    }

    public BaseCombiner addBefore(BaseHandler beforeHandler){
        handler.registerBeforeHandle(beforeHandler);
        return this;
    }

    public BaseCombiner addAfter(BaseHandler afterHandler){
        handler.registerAfterHandle(afterHandler);
        return this;
    }

    public BaseHandler getHandler(){
        return handler;
    }

    public BaseCombiner completeAndRegiestHandler() throws ContextException {
        applicationContextApi.registerBean(null,handler);
        return this;
    }

    public boolean isInint(){
        return handler != null;
    }

}
