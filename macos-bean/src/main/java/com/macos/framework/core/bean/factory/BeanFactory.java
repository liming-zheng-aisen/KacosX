package com.macos.framework.core.bean.factory;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/20
 * @description 对bean加工的工厂，主要功能：创建bean实例、为bean的字段赋值
 */
public class BeanFactory {

    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);

    /**
     * 根据类全路径，创建一个实例
     *
     * @param clazz
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object createNewBean(String clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class c = Class.forName(clazz);
        return c.newInstance();
    }

    /**
     * 根据已经加载的class创建一个实例
     *
     * @param c
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object createNewBean(Class c) throws IllegalAccessException, InstantiationException {
        return c.newInstance();
    }

    /**
     * 根据已经加载的class创建一个实例
     *
     * @param c
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object initNewBean(Class c, Properties evn) throws Exception {
        Object object=createNewBean(c);
        AutowiredFactory.doAutowired(object,evn);
        ValueFactory.doFields(object,evn);
        return object;
    }
    /**
     * 根据已经加载的class创建一个实例
     *
     * @param c
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object initNewBean(String c, Properties evn) throws Exception {
        Object object=createNewBean(c);
        AutowiredFactory.doAutowired(object,evn);
        ValueFactory.doFields(object,evn);
        return object;
    }



    /**
     * 通过调用已存在的bean的方法创建一个实例（该方法上必须有@DyBean注解）
     *
     * @param bean 已存在的实例
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> doMethodsInitialBean(Object bean) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = bean.getClass().getDeclaredMethods();
        Map<String, Object> beans = new HashMap<String, Object>();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Bean.class)) {
                Bean dyBean = m.getAnnotation(Bean.class);
                m.setAccessible(true);
                Object mbean = m.invoke(bean, null);
                if (StringUtils.isEmptyPlus(dyBean.value())) {
                    String beanName = mbean.getClass().getSuperclass().getSimpleName();
                    beans.put(StringUtils.toLowerCaseFirstName(beanName), mbean);
                } else {
                    beans.put(dyBean.value(), mbean);
                }
            }
        }
        return beans;
    }

    public static  Class classLoad(String str) throws ClassNotFoundException {
        return Class.forName(str);
    }


}
