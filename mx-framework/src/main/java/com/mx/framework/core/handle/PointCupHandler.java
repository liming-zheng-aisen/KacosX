package com.mx.framework.core.handle;

import com.mx.aop.core.mananger.PointCupMananger;
import com.mx.common.util.ReflectionsUtil;
import com.mx.framework.annotation.aop.Pointcut;
import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.handle.base.BaseHandler;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @Desc 切入点
 * @Author Zheng.LiMing
 * @Date 2020/4/1
 */
public class PointCupHandler extends BaseHandler {

    public PointCupHandler() {
        handleAnnotations = new Class[]{Pointcut.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (!doBefore(mainClass, handleClass, args)) {
            return false;
        }

        Set<BeanDefinition> beanDefinitions = BeanManager.getClassContainer();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class target = beanDefinition.getTarget();
            List<Method> methodList = ReflectionsUtil.getMethodsByAnnotation(target, handleAnnotations[0]);
            if (methodList == null || methodList.size() == 0) {
                continue;
            }

            for (Method method : methodList) {
                Pointcut pointcut = method.getAnnotation(Pointcut.class);
                PointCupMananger.regiest(method.getName() + "()", pointcut.value());
            }

        }

       return doAfter(mainClass,handleClass,args);

    }
}
