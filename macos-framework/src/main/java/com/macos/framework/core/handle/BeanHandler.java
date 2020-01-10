package com.macos.framework.core.handle;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Bean;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;


/**
 * @Desc BeanHandle
 * @Author Zheng.LiMing
 * @Date 2020/1/6
 */
public class BeanHandler extends BaseHandler {

    static {
        handleAnnotations = new Class[]{Bean.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {
        BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null, handleClass);
        Class currentHandleClass = beanDefinition.getTarget();
        //创建并注册当前实例
        if (doBefore(mainClass, currentHandleClass, args)) {

        }
        //执行后置处理
        if (doAfter(mainClass, currentHandleClass, args) && nextHandler!=null){
            nextHandler.doHandle(mainClass,handleClass,args);
        }
        return true;
    }

    private String getBeanName(Class target) {
        String beanName = target.getName();
        Bean bean = (Bean) target.getAnnotation(Bean.class);
        if (StringUtils.isNotEmptyPlus(bean.value())) {
            beanName = bean.value();
        }
        return beanName;
    }
}
