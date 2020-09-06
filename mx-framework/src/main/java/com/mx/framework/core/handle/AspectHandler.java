package com.mx.framework.core.handle;

import com.mx.framework.annotation.aop.Aspect;
import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.handle.base.BaseHandler;

/**
 * @Desc 切面处理器
 * @Author Zheng.LiMing
 * @Date 2020/4/1
 */
public class AspectHandler extends BaseHandler {

    public AspectHandler() {
        handleAnnotations = new Class[]{Aspect.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {
        if (!needToHandle(handleClass)){
            return true;
        }

        if(!doBefore(mainClass,handleClass,args)){
           return false;
        }

        BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null,handleClass,true);

        if (beanDefinition!=null){
            newInstance(beanDefinition,null);
        }

        return doAfter(mainClass,handleClass,args);
    }
}
