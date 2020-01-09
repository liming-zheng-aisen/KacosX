package com.macos.framework.core.handle;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Configuration;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;

import java.util.Set;


/**
 * @Desc 配置类处理器
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
@SuppressWarnings("all")
public class ConfigurationHandler extends BaseHandler {

    static {
        handleAnnotations = new Class[]{Configuration.class};
    }

    /**
     * 实例化配置类，并执行前置通知和后置通知
     *
     * @param mainClass   程序入口对象
     * @param handleClass 当前处理对象
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {
        Set<BeanDefinition> classContainer = BeanManager.getBeanDefinitionsByAnnotation(handleAnnotations);
        for (BeanDefinition beanDefinition : classContainer) {
            Class currentHandleClass = beanDefinition.getTarget();
            //执行前置处理
            doBefore(mainClass,currentHandleClass, args);
            //创建并注册当前实例
            newInstance(beanDefinition,getBeanName(currentHandleClass));
            //执行后置处理
            doAfter(mainClass,currentHandleClass, args);
        }
        return true;
    }

    /**
     * 获取bean的名字
     * @param target
     * @return
     */
    private String getBeanName(Class target) {
        String beanName = target.getName();
        Configuration configuration = (Configuration) target.getAnnotation(Configuration.class);
        if (StringUtils.isNotEmptyPlus(configuration.value())) {
            beanName = configuration.value();
        }
        return beanName;
    }


}
