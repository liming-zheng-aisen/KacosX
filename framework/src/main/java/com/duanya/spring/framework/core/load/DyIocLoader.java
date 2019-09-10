package com.duanya.spring.framework.core.load;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyComponent;
import com.duanya.spring.framework.annotation.DyConfiguration;
import com.duanya.spring.framework.annotation.DyService;
import com.duanya.spring.framework.context.exception.DyContextException;
import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
import com.duanya.spring.framework.core.bean.factory.DyAutowiredFactory;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.bean.factory.DyValueFactory;
import com.duanya.spring.framework.core.bean.factory.bean.manager.DyBeanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description
 */

public class DyIocLoader extends DyBeanLoad{


    private static final Logger log = LoggerFactory.getLogger(DyIocLoader.class);

    private static DySpringApplicationContext applicationContent;


    public DyIocLoader(){

    }

    public DyIocLoader(DyBeanLoad beanLoad){
        nextLoader=beanLoad;
    }
    @Override
    public void load(Class c) throws Exception {
       if (null==applicationContent) {
           applicationContent = DySpringApplicationContext.Builder.getDySpringApplicationContext();
       }
        loadBean();
        initConfigurationBean();
        doAutowirteAll();
        log.info("上下文容器初始化成功");

        if (null!=nextLoader){
            nextLoader.load(c);
        }
    }

    private static void loadBean() throws ClassNotFoundException, InstantiationException, IllegalAccessException, DyContextException {

        Set<Class> clazzs=DyBeanManager.getClassContainer();

        for (Class beanClass:clazzs){

            if (beanClass.isAnnotationPresent(DyService.class)
                    ||beanClass.isAnnotationPresent(DyComponent.class)||beanClass.isAnnotationPresent(DyConfiguration.class)){

                Object bean = DyBeanFactory.createNewBean(beanClass);

                DyValueFactory.doFields(bean,DyConfigurationLoader.getEvn());

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
                DyValueFactory.doFields(bean,DyConfigurationLoader.getEvn());

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
               if (bean.getClass().isAnnotationPresent(DyConfiguration.class)) {
                   DyAutowiredFactory.doAutowired(bean);
                   //调用bean的方法创建实例
                   Map<String, Object> beans = DyBeanFactory.doMethodsInitialBean(bean);
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
            DyAutowiredFactory.doAutowired(context.get(key));
        }
        log.info("doAutowirteAll，配置类自动装配完成！");
    }

    private static String getAnnotationValue(Class c){
        String result=null;
        if (c.isAnnotationPresent(DyService.class)){
            DyService dyService= (DyService) c.getAnnotation(DyService.class);
            result=dyService.value();
        }else if (c.isAnnotationPresent(DyComponent.class)){
            DyComponent dyComponent= (DyComponent) c.getAnnotation(DyComponent.class);
            result=dyComponent.value();
        }
        return result;
    }


}
