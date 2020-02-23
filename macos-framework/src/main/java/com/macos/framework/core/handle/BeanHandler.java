package com.macos.framework.core.handle;

import com.macos.common.util.ReflectionsUtil;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Autowired;
import com.macos.framework.annotation.Bean;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;


/**
 * @Desc Bean处理器
 * @Author Zheng.LiMing
 * @Date 2020/1/6
 */
public class BeanHandler extends BaseHandler {

    public BeanHandler() {
        handleAnnotations = new Class[]{Bean.class,Autowired.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (!needToHandle(handleClass)) {
            return true;
        }

        BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null, handleClass,true);
        Class currentHandleClass = beanDefinition.getTarget();
        Object currentHandleBean = beanDefinition.getContextApi().getBean(null,handleClass);
        if (doBefore(mainClass, currentHandleClass, args)) {
            List<Method> methods = ReflectionsUtil.getMethodsByAnnotation(currentHandleClass,handleAnnotations[0]);
            for (Method method:methods){
               invoke(currentHandleBean,method);
            }
        }
        //执行后置处理
        return  doAfter(mainClass,currentHandleClass, args);
    }

    /**
     * 执行method方法
     * @param currentHandleBean
     * @param method
     * @throws Exception
     */
    private void invoke(Object currentHandleBean,Method method) throws Exception {
        Class returnType = ReflectionsUtil.getMethodReturnType(method);
        BeanDefinition methodBeanDefinition = BeanManager.getBeanDefinition(null,returnType,true);
        if (methodBeanDefinition==null){
            BeanManager.registerClass(returnType);
            methodBeanDefinition = BeanManager.getBeanDefinition(null,returnType,false);
        }
        Parameter[] parameters = ReflectionsUtil.getMethodParams(method);
        Object methodBean = ReflectionsUtil.invokeMethod(currentHandleBean,method,getParameterValue(parameters));
        String beanName = getBeanName(method,returnType);
        methodBeanDefinition.setBeanName(beanName);
        methodBeanDefinition.setContextApi(applicationContextApi);
        applicationContextApi.registerBean(beanName,methodBean);
    }

    /**
     * 获取参数对象
     * @param parameters
     * @return
     * @throws Exception
     */
    private Object[] getParameterValue( Parameter[] parameters) throws Exception {
        if (parameters ==null || parameters.length ==0){
            return null;
        }

        Object[] objs = new Object[parameters.length];
        for (int i=0;i<parameters.length;i++){
            Parameter parameter = parameters[i];
            Class p = ReflectionsUtil.getClassByParameter(parameter);
            String beanName = null;
            if (parameter.isAnnotationPresent(Autowired.class)){
                Autowired autowired = parameter.getAnnotation(Autowired.class);
                beanName = autowired.value();
            }
            BeanDefinition beanDefinition = BeanManager.getBeanDefinition(beanName,p,false);
            objs[i] = beanDefinition.getContextApi().getBean(beanName,p);
        }
        return objs;
    }


    /**
     * 获取bean的名字
     * @param target
     * @param returnType
     * @return
     */

    private String getBeanName(Method target,Class returnType) {
        String beanName = returnType.getName();
        Bean bean = target.getAnnotation(Bean.class);
        if (StringUtils.isNotEmptyPlus(bean.value())) {
            beanName = bean.value();
        }
        return beanName;
    }


}
