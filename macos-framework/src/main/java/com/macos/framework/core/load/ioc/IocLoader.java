package com.macos.framework.core.load.ioc;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.*;
import com.macos.framework.context.impl.ApplicationContextImpl;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.proxy.BeanProxy;
import com.macos.framework.core.load.abs.BeanLoad;
import com.macos.framework.core.load.conf.ConfigurationLoader;
import com.macos.framework.core.util.BeanUtil;
import com.macos.framework.core.util.FieldUtil;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description IOC加载器
 */
@Slf4j
public class IocLoader extends BeanLoad {

    private static ApplicationContextImpl applicationContent;


    public IocLoader(){

    }


    /**
     * 加载
     * @param c
     * @throws Exception
     */
    @Override
    public void load(Class c) throws Exception {
       if (null==applicationContent) {
           applicationContent = ApplicationContextImpl.Builder.getApplicationContext();
       }

        loadBean();
        initConfigurationBean();
        doAutowirteAll();
        log.info("上下文容器初始化成功");
    }

    private static void loadBean() throws Exception {

        Set<BeanDefinition> clazzs= BeanManager.getClassContainer();

        for (BeanDefinition beanDefinition:clazzs){

            Class beanClass = beanDefinition.getTarget();

            if (beanClass.isAnnotationPresent(Service.class)
                    ||beanClass.isAnnotationPresent(Component.class)||beanClass.isAnnotationPresent(Configuration.class)){

                Object bean = BeanUtil.createNewBean(beanClass);

                FieldUtil.doFields(bean);

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
                FieldUtil.doFields(bean);

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
                   doAutowired(bean,ConfigurationLoader.getEvn());
                   //调用bean的方法创建实例
                   Map<String, Object> beans = BeanUtil.doMethodsInitialBean(bean);
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
           doAutowired(context.get(key),ConfigurationLoader.getEvn());
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

    public static void doAutowired(Object bean, Properties evn) throws Exception {
        if (null==bean){
            return;
        }
        Field[] fields= bean.getClass().getDeclaredFields();
        for (Field f:fields){
            if (f.isAnnotationPresent(Autowired.class)){
                f.setAccessible(true);
                Autowired dyAutowired=f.getAnnotation(Autowired.class);
                String beanName=dyAutowired.value();
                Class beanClass=null;
                if (StringUtils.isEmptyPlus(beanName)){
                    String fName=f.getType().getCanonicalName();
                    beanClass= Class.forName(fName);
                    beanName=StringUtils.toLowerCaseFirstName(beanClass.getSimpleName());
                }
                Object fBean=BeanManager.getBeanDefinition(beanName,beanClass).getContextApi().getBean(beanName,beanClass);
                if (fBean==null){
                    if (f.getType().isAnnotationPresent(RestAPI.class)){
                        fBean= BeanUtil.initNewBean(f.getType().getName(),evn);
                    }else {
                        throw new Exception(beanClass.getName().toString() + "为空！");
                    }
                }
                Class[] classes=f.getType().getInterfaces();
                Class[] classes1=new Class[classes.length+1];
                System.arraycopy(classes,0,classes1,0,classes.length);
                classes1[classes1.length-1]=f.getType();
                BeanProxy beanProxy =new BeanProxy(fBean);
                Object object = Proxy.newProxyInstance(f.getType().getClassLoader(),classes1, beanProxy);
                f.set(bean,object);

            }
        }
    }

}
