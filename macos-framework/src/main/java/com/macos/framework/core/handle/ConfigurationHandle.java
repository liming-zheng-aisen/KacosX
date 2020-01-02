package com.macos.framework.core.handle;

import com.macos.framework.annotation.Configuration;
import com.macos.framework.annotation.MacosXApplication;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandle;
import com.macos.framework.core.util.AnnotationUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Desc @Configuration处理类
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
public class ConfigurationHandle implements BaseHandle {

    private static Map<Class,BaseHandle> handleMap = new HashMap<>();

    @Override
    public void doHandle(Class c) throws Exception {
        if (AnnotationUtil.hasAnnotion(c,new Class[]{Configuration.class,MacosXApplication.class})){
           registePathBeanDefinition(c);
        }
        doHandleMap();

    }

    /**
     * 通过注解信息对其进行处理
     * @throws Exception
     */
    public void doHandleMap() throws Exception {
        Set<BeanDefinition> classContainer = BeanManager.getClassContainer();
        for (BeanDefinition beanDefinition: classContainer) {
            Class c = beanDefinition.getTarget();
            if (AnnotationUtil.hasAnnotion(c, new Class[]{Configuration.class, MacosXApplication.class})) {
                notifyHandle(c);
            }
        }
    }

    /**
     * 调用对应的handle进行处理
     * @param c
     * @throws Exception
     */
    public void notifyHandle(Class c) throws Exception {
        Annotation[] annotations = c.getAnnotations();
        if (annotations==null||annotations.length==0){
            return;
        }
        for (Annotation an:annotations){
           BaseHandle handle = handleMap.get(an.annotationType());
           if (handle!=null){
               handle.doHandle(c);
           }
        }
    }

    private void registePathBeanDefinition(Class c) throws Exception {
           BeanManager beanManager =  new BeanManager();
           beanManager.registerBean(null,c);
    }

    public static void registerHandle(Class key,BaseHandle value) {
        ConfigurationHandle.handleMap.put(key,value);
    }

}
