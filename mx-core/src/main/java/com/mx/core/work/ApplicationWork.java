package com.mx.core.work;

import com.mx.core.combiner.*;
import com.mx.framework.annotation.aop.Aspect;
import com.mx.framework.annotation.core.Component;
import com.mx.framework.annotation.core.Configuration;
import com.mx.framework.annotation.core.Service;
import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.core.bean.manage.BeanManager;

import java.util.Set;

/**
 * @Desc 应用程序工作
 * @Author Zheng.LiMing
 * @Date 2020/2/20
 */
public class ApplicationWork {

    /**
     * 开始工作
     */
    public static void work() throws Exception {
        work(ApplicationWork.class,new String[]{});
    }

    /**
     * 开始工作
     * @param main
     * @param args
     */
    public static void work(Class main,String[] args) throws Exception {

        if (BeanManager.isLoad()){
            classLoader(main,args);
        }

        Set<BeanDefinition> beanDefinitions = BeanManager.getClassContainer();
        for (BeanDefinition beanDefinition : beanDefinitions){
            Class target = beanDefinition.getTarget();
            if (target.isAnnotationPresent(Configuration.class)){
                configurationLoader(main,target,args);
            }else if (target.isAnnotationPresent(Component.class)){
                doComponent(main,target,args);
            }else if (target.isAnnotationPresent(Service.class)){
                doService(main,target,args);
            }else if (target.isAnnotationPresent(Aspect.class)){
                aopLoader(main,target,args);
            }
        }
    }

    /**
     * 加载类
     * @param main
     * @param args
     */
    public static void classLoader(Class main,String[] args) throws Exception {
        MacosXScannerCombiner.getCombiner()
                .getHandler()
                .doHandle(main,main,args);
    }


    /***
     * aop加载
     * @param main
     * @param handlerClass
     * @param args
     * @throws Exception
     */
    public static void aopLoader(Class main,Class handlerClass,String[] args) throws Exception {
        AspectCombiner.getCombiner()
                .getHandler()
                .doHandle(main,handlerClass,args);
    }

    /**
     * 加载配置
     * @param main
     * @param args
     */
    public static void configurationLoader(Class main,Class handlerClass,String[] args) throws Exception {
        ConfigurationCombiner.getCombiner()
                .getHandler()
                .doHandle(main,handlerClass,args);
    }

    /**
     * 处理Component
     * @param main
     * @param handlerClass
     * @param args
     * @throws Exception
     */
    public static void doComponent(Class main,Class handlerClass ,String[] args) throws Exception {
        ComponentCombiner.getCombiner().getHandler().doHandle(main,handlerClass,args);
    }

    /**
     * 处理Service
     * @param main
     * @param handlerClass
     * @param args
     * @throws Exception
     */
    public static void doService(Class main,Class handlerClass ,String[] args) throws Exception {
        ServiceCombiner.getCombiner().getHandler().doHandle(main,handlerClass,args);
    }


}
