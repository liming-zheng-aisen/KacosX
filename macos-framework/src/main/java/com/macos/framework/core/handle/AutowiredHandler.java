package com.macos.framework.core.handle;

import com.macos.common.util.ReflectionsUtil;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Autowired;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;

/**
 * @Desc 依赖注入
 * @Author Zheng.LiMing
 * @Date 2020/1/8
 */
public class AutowiredHandler extends BaseHandler {

    public AutowiredHandler() {
        handleAnnotations = new Class[]{Autowired.class};
    }


    /**
     * 依赖注入
     * @param mainClass 程序入口对象
     * @param handleClass 当前处理对象
     * @param args
     * @return
     * @throws Exception
     */
    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (doBefore(mainClass,handleClass,args)){
            BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null,handleClass,true);
            if (beanDefinition!=null){
               Object bean = beanDefinition.getContextApi().getBean(null,handleClass);
                if (bean!=null){
                    doField(handleClass,bean);
                }
            }
        }
        return doAfter(mainClass,handleClass,args);
    }

    /**
     * 为属性赋值
     * @param handleClass
     * @param bean
     * @throws Exception
     */
    private void doField(Class handleClass,Object bean) throws Exception {
        Field[] fields = handleClass.getDeclaredFields();
        for (Field field:fields){
            if (!field.isAnnotationPresent(Autowired.class)){
                continue;
            }
           String beanName = getBeanName(field);
           BeanDefinition beanDefinition = BeanManager.getBeanDefinition(beanName,field.getType(),true);
           Object value = beanDefinition.getContextApi().getBean(beanName,field.getType());
           ReflectionsUtil.setFieldValue(bean,field,value);
        }
    }

    private String getBeanName(Field field){
        String beanName = null;
        if (field.isAnnotationPresent(Autowired.class)){
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (StringUtils.isNotEmptyPlus(autowired.value())) {
                beanName = autowired.value();
            }
        }
        return beanName;
    }
}
