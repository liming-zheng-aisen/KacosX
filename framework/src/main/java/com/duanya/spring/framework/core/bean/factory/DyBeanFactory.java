package com.duanya.spring.framework.core.bean.factory;

import com.duanya.spring.commont.util.StringUtils;
import com.duanya.spring.framework.context.spring.DySpringApplicationContent;
import com.duanya.spring.framework.core.annotation.DyAutowired;
import com.duanya.spring.framework.core.annotation.DyBean;
import com.duanya.spring.framework.core.annotation.DyValue;

import java.lang.reflect.Field;
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
public class DyBeanFactory {

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
     * 为字段赋值（字段上必须有@DyValue的注解）
     *
     * @param bean
     * @param evn
     * @return
     * @throws IllegalAccessException
     */
    public static Object doFields(Object bean, Properties evn) throws IllegalAccessException {

        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {

            field.setAccessible(true);

            if (field.isAnnotationPresent(DyValue.class)) {
                DyValue dyValue = field.getAnnotation(DyValue.class);
                String value = dyValue.value();
                Object fValue = null;
                if (StringUtils.isNotEmptyPlus(value)) {
                    String fName = value.substring(0, 2);
                    int fEndIdnex = value.lastIndexOf("}");
                    if ("${".equals(fName) && fEndIdnex > 1) {
                        String key = value.substring(2, fEndIdnex);
                        fValue = evn.get(key);
                        if (null == fValue) {
                            if (fEndIdnex < value.length() - 1) {
                                if (value.substring(fEndIdnex, fEndIdnex + 1).equals(":")) {
                                    fValue = value.substring(fEndIdnex + 2);
                                } else {
                                    throw new IllegalAccessException(value + "不符合规范【@DyValue(\"${key}:默认值\")】");
                                }
                            }
                        }
                    } else {
                        fValue = value;
                    }
                    setField(field,bean,fValue);

                }
            }
        }
        return bean;
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
            if (m.isAnnotationPresent(DyBean.class)) {
                DyBean dyBean = m.getAnnotation(DyBean.class);
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


    public static void doAutowired(Object bean) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (null==bean){
            return;
        }
        DySpringApplicationContent context=new DySpringApplicationContent();
        Field[] fields= bean.getClass().getDeclaredFields();
        for (Field f:fields){
            if (f.isAnnotationPresent(DyAutowired.class)){
                f.setAccessible(true);
                DyAutowired dyAutowired=f.getAnnotation(DyAutowired.class);
                String beanName=dyAutowired.value();
                if (StringUtils.isEmptyPlus(beanName)){
                   String fName=f.getType().getCanonicalName();
                   Class c= Class.forName(fName);
                   String cName = c.getClass().getSuperclass().getSimpleName();
                   if (cName.equals("Object")){
                       beanName=c.getSimpleName();
                   }else{
                       beanName=c.getClass().getSuperclass().getSimpleName();
                   }
                   beanName=StringUtils.toLowerCaseFirstName(beanName);
                }
                Object fBean=context.getBean(beanName);
                f.set(bean,fBean);

            }
        }
    }
    private static void setField(Field field, Object bean,Object value) throws IllegalAccessException {
        String fType=field.getType().toString();
        if (fType.endsWith("String")){
            field.set(bean,(String)value);
        }else if (fType.endsWith("int") || fType.endsWith("Integer")){
            field.set(bean,Integer.parseInt(value.toString()));
        }else if (fType.endsWith("Double") || fType.endsWith("double")){
            field.set(bean,Double.valueOf(value.toString()));
        }else if (fType.endsWith("Boolean") || fType.endsWith("boolean")){
            field.set(bean,Boolean.valueOf(value.toString()));
        }else if (fType.endsWith("Short") || fType.endsWith("short")){
            field.set(bean,Short.valueOf(value.toString()));
        }else if (fType.endsWith("Float") || fType.endsWith("float")){
            field.set(bean,Float.valueOf(value.toString()));
        }else if (fType.endsWith("Character") || fType.endsWith("char")){
            field.set(bean,(char)value);
        }
    }

}
