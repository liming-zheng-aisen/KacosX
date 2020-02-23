package com.macos.framework.core.handle;

import com.macos.common.util.ReflectionsUtil;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.ConfigurationProperties;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.core.handle.base.BaseHandler;

import java.lang.reflect.Field;

/**
 * @Desc 为配置文件添加前缀,并赋值给属性
 * @Author Zheng.LiMing
 * @Date 2020/1/12
 */
public class ConfigurationPropertiesHandler extends BaseHandler {

    public ConfigurationPropertiesHandler() {
        handleAnnotations = new Class[]{ConfigurationProperties.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (!needToHandle(handleClass)) {
            return true;
        }

        if (doBefore(mainClass,handleClass,args)){
        BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null,handleClass,true);
        if (beanDefinition!=null) {
            Class target = beanDefinition.getTarget();
            if (target.isAnnotationPresent(handleAnnotations[0])){
                ConfigurationProperties configurationProperties = (ConfigurationProperties) target.getAnnotation(ConfigurationProperties.class);
                String prefix = configurationProperties.prefix();
                if (StringUtils.isNotEmptyPlus(prefix)){
                    beanDefinition.setPrefix(prefix);
                }
                doField(beanDefinition);
            }
        }
      }
      return doAfter(mainClass,handleClass,args);
    }

    private void doField(BeanDefinition beanDefinition) throws Exception {
        Class target = beanDefinition.getTarget();
        Field[] fields = target.getDeclaredFields();
        if (fields!=null && fields.length>0) {
            BeanDefinition envBeanDefinition = BeanManager.getBeanDefinition(null,ApplicationENV.class,true);
            if (envBeanDefinition==null){
                return;
            }
            Object bean = beanDefinition.getContextApi().getBean(null,target);
            ApplicationENV env = (ApplicationENV)envBeanDefinition.getContextApi().getBean(null,ApplicationENV.class);
            for (Field field : fields) {
                String name = field.getName();
                String key = name;
                if (StringUtils.isNotEmptyPlus(beanDefinition.getPrefix())){
                   key = beanDefinition.getPrefix() + "." + name;
                }
                Object value = env.getElementValue(key);
                if (value!=null){
                    ReflectionsUtil.setFieldValue(bean,field,value);
                }
            }

        }
    }


}
