package com.duanya.spring.framework.starter.context;

import com.duanya.spring.framework.context.base.DyApplicationContext;
import com.duanya.spring.framework.context.exception.DyContextException;
import com.duanya.spring.framework.context.manager.DyContextManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc DyStarterContext
 * @Author Zheng.LiMing
 * @Date 2019/9/8
 */
public class DyStarterContext implements DyApplicationContext {

    private static Map<String,Object> starterContext=new HashMap<>();

    @Override
    public Object getBean(String beanName, Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return starterContext.get(beanName);
    }

    @Override
    public void registerBean(String beanName, Object object) throws DyContextException {
        if (starterContext.containsKey(beanName)){
            throw new DyContextException(beanName+"已存在！");
        }
        starterContext.put(beanName,object);

    }

    private DyStarterContext(){

    }

    public static class Builder{
        private final static DyStarterContext context=new DyStarterContext();
        public static DyStarterContext getContext(){
            DyContextManager contextManager=DyContextManager.BuilderContext.getContextManager();
            if (!contextManager.hasContext(context)){
                contextManager.registerApplicationContext(context);
            }
            return context;
        }
    }
}
