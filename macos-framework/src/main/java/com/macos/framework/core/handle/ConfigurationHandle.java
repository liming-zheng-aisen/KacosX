package com.macos.framework.core.handle;

import com.macos.common.util.AnnotationUtil;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Configuration;
import com.macos.framework.context.exception.ContextException;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandle;
import com.macos.framework.core.util.BeanUtil;

import java.util.Set;


/**
 * @Desc @Configuration处理类
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
public class ConfigurationHandle extends BaseHandle {

    static {
        annotationclass=new Class[]{Configuration.class};
    }

    /**
     * 实例化配置类，并执行前置通知和后置通知
     * @param target
     * @param args
     * @throws Exception
     */
    @Override
    public boolean doHandle(Class target,String[] args) throws Exception {
        if (AnnotationUtil.hasAnnotion(target,annotationclass)){
           registePathBeanDefinition(target);
        }
        Set<BeanDefinition> classContainer = BeanManager.getBeanDefinitionsByAnnotation(annotationclass);
        for (BeanDefinition beanDefinition : classContainer){
            doBefore(beanDefinition.getTarget());
            newConfiguration(beanDefinition);
            doAfter(beanDefinition.getTarget());
        }
        return true;
    }

    /**
     * 创建配置类实例，并注册到上下文中
     * @param beanDefinition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ContextException
     */
    private Object newConfiguration(BeanDefinition beanDefinition) throws InstantiationException, IllegalAccessException, ContextException {
        Class target = beanDefinition.getTarget();
        if (target.isInterface()){
            return null;
        }
        Object config = BeanUtil.createNewBean(target);

        String beanName = target.getName();
        Configuration configuration = (Configuration) target.getAnnotation(Configuration.class);
        if (StringUtils.isEmptyPlus(configuration.value())){
            beanName = configuration.value();
        }
        beanDefinition.setBeanName(beanName);
        beanDefinition.setContextApi(applicationContextApi);
        applicationContextApi.registerBean(beanName,config);
        return config;
    }


    /**
     * 注册开始调用方的类信息，也就是main的class信息
     * @param c
     * @throws Exception
     */
    private void registePathBeanDefinition(Class c) throws Exception {
           BeanManager beanManager =  new BeanManager();
           beanManager.registerBean(null,c);
    }



}
