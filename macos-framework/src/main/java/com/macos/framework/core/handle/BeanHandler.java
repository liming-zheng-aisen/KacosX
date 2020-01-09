package com.macos.framework.core.handle;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Bean;
import com.macos.framework.annotation.Component;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;

import java.util.Set;

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
        Set<BeanDefinition> classContainer = BeanManager.getBeanDefinitionsByAnnotation(handleAnnotations);
        for (BeanDefinition beanDefinition : classContainer){
            Class currentHandleClass = beanDefinition.getTarget();
            //执行前置处理
            doBefore(mainClass,currentHandleClass, args);
            //创建并注册当前实例
            newInstance(beanDefinition,getBeanName(currentHandleClass));
            //执行后置处理
            doAfter(mainClass,currentHandleClass, args);
        }
        return false;
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
