package com.macos.framework.core.load.ioc;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Component;
import com.macos.framework.annotation.Configuration;
import com.macos.framework.annotation.Service;
import com.macos.framework.context.exception.ContextException;
import com.macos.framework.context.ApplicationContextImpl;
import com.macos.framework.core.bean.factory.AutowiredFactory;
import com.macos.framework.core.bean.factory.BeanFactory;
import com.macos.framework.core.bean.factory.ValueFactory;
import com.macos.framework.core.bean.BeanManager;
import com.macos.framework.core.load.abs.BeanLoad;
import com.macos.framework.core.load.conf.ConfigurationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description IOC加载器
 */

public class IocLoader extends BeanLoad {


    private static final Logger log = LoggerFactory.getLogger(IocLoader.class);

    private static ApplicationContextImpl applicationContent;


    public IocLoader(){

    }

    public IocLoader(BeanLoad beanLoad){
        nextLoader=beanLoad;
    }

    /**
     * 加载
     * @param c
     * @throws Exception
     */
    @Override
    public void load(Class c) throws Exception {
       if (null==applicationContent) {
           applicationContent = ApplicationContextImpl.Builder.getDySpringApplicationContext();
       }
        loadBean();
        initConfigurationBean();
        doAutowirteAll();
        log.info("上下文容器初始化成功");
        if (null!=nextLoader){
            nextLoader.load(c);
        }
    }

    private static void loadBean() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ContextException {

        Set<Class> clazzs= BeanManager.getClassContainer();

        for (Class beanClass:clazzs){

            if (beanClass.isAnnotationPresent(Service.class)
                    ||beanClass.isAnnotationPresent(Component.class)||beanClass.isAnnotationPresent(Configuration.class)){

                Object bean = BeanFactory.createNewBean(beanClass);

                ValueFactory.doFields(bean, ConfigurationLoader.getEvn());

                String beanName=getAnnotationValue(beanClass);

                if (StringUtils.isEmptyPlus(beanName)){
                    Class[] interfaceClass =  beanClass.getInterfaces();
                    if (interfaceClass.length>1){
                        throw new InstantiationException("目标对象"+beanClass.getName()+"实现多个接口，请标注beanName名称以便区分!");
                    }else if (interfaceClass.length==1){
                        String iName=StringUtils.toLowerCaseFirstName(interfaceClass[0].getSimpleName());
                        applicationContent.registerBean(iName,bean);
                    }
                     String oName=StringUtils.toLowerCaseFirstName(beanClass.getSimpleName());
                     applicationContent.registerBean(oName,bean);
                }else {
                    applicationContent.registerBean(beanName,bean);

                }
                ValueFactory.doFields(bean, ConfigurationLoader.getEvn());

            }
        }
        log.info("loadBean加载bean到DySpringApplicationContent上下文中");
    }

    private static void initConfigurationBean() throws Exception {
        Map<String,Object> context=applicationContent.getContext();
        Iterator<String> iterator=context.keySet().iterator();
        List<Map<String,Object>> list=new ArrayList< Map<String,Object>>();
        while (iterator.hasNext()){
            String key=iterator.next();
               Object bean= context.get(key);
               if (bean.getClass().isAnnotationPresent(Configuration.class)) {
                   AutowiredFactory.doAutowired(bean,ConfigurationLoader.getEvn());
                   //调用bean的方法创建实例
                   Map<String, Object> beans = BeanFactory.doMethodsInitialBean(bean);
                   list.add(beans);
               }
        }
        //将结果放入dyspring的上下文
       for (Map<String,Object> itme:list){
           applicationContent.registeredBeanMap(itme);
       }
        log.info("initConfigurationBean初始化配置类，并调用@DyBean的方法初始化一个bean注册到DySpringApplicationContent上下文中");
    }

    private static void doAutowirteAll() throws Exception {
        Map<String,Object> context=applicationContent.getContext();
        Iterator iterator=context.keySet().iterator();
        while (iterator.hasNext()){
            String key=(String)iterator.next();
            AutowiredFactory.doAutowired(context.get(key),ConfigurationLoader.getEvn());
        }
        log.info("doAutowirteAll，配置类自动装配完成！");
    }

    private static String getAnnotationValue(Class c){
        String result=null;
        if (c.isAnnotationPresent(Service.class)){
            Service service = (Service) c.getAnnotation(Service.class);
            result= service.value();
        }else if (c.isAnnotationPresent(Component.class)){
            Component component = (Component) c.getAnnotation(Component.class);
            result= component.value();
        }
        return result;
    }


}
