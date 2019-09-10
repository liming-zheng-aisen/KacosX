package com.duanya.spring.framework.jdbc.context;

import com.duanya.spring.framework.context.base.DyApplicationContext;
import com.duanya.spring.framework.context.exception.DyContextException;
import com.duanya.spring.framework.context.manager.DyContextManager;

import java.util.HashMap;
import java.util.Map;


/**
 * @Desc DyJdbcContext
 * @Author Zheng.LiMing
 * @Date 2019/9/5
 */
public class DyJdbcContext implements DyApplicationContext {



    private static Map<String,Object> jdbcContext=new HashMap<>();



    @Override
    public Object getBean(String beanName,Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        if (jdbcContext.containsKey(beanName)){
            return jdbcContext.get(beanName);
        }

       return null;
    }


    @Override
    public void registerBean(String beanName, Object object) throws DyContextException {
        jdbcContext.put(beanName, object);
    }



    public static class Builder{

        /**单例模式*/
        private final static DyJdbcContext context=new DyJdbcContext();

        public static  DyJdbcContext getDySpringApplicationContext(){
            DyContextManager contextManager=DyContextManager.BuilderContext.getContextManager();
            if (!contextManager.hasContext(context)){
               contextManager.registerApplicationContext(context);
            }
            return context;
        }
    }

}
