package com.macos.framework.core.handle;

import com.macos.common.properties.PropertiesType;
import com.macos.common.properties.PropetiesUtil;
import com.macos.framework.annotation.PropertySource;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.core.handle.base.BaseHandler;

import java.util.Map;
import java.util.Properties;

/**
 * @Desc 导入配置文件
 * @Author Zheng.LiMing
 * @Date 2020/1/6
 */
public class PropertySourceHandler extends BaseHandler {

   public PropertySourceHandler() {
        handleAnnotations = new Class[]{
                PropertySource.class
        };
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (!needToHandle(handleClass)) {
            return true;
        }

        if (doBefore(mainClass, handleClass, args)) {
            if (handleClass.isAnnotationPresent(handleAnnotations[0])) {
                PropertySource propertySource = (PropertySource) handleClass.getAnnotation(PropertySource.class);
                String[] sources = propertySource.source();
                if (sources.length > 0) {
                    doLoadSourceToEnv(sources,handleClass);
                }
            }
        }
        return doAfter(mainClass, handleClass, args);
    }

    public void doLoadSourceToEnv(String[] source, Class handleClass) throws Exception {
        BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null, ApplicationENV.class, true);

        ApplicationENV env = null;

        if (beanDefinition == null) {
            BeanManager.registerClass(ApplicationENV.class);
            beanDefinition = BeanManager.getBeanDefinition(null, ApplicationENV.class, true);
            env = new ApplicationENV();
            applicationContextApi.registerBean(null, env);
            beanDefinition.setContextApi(applicationContextApi);
        } else {
            env = (ApplicationENV) beanDefinition.getContextApi().getBean(null, ApplicationENV.class);
        }

        for (String s : source) {
            if (s.endsWith(PropertiesType.YAML_SUFFIC) || s.endsWith(PropertiesType.YML_SUFFIC)) {
                Map map = PropetiesUtil.loadYaml(s, handleClass);
                env.addElementByMap(map);
            } else if (s.equals(PropertiesType.PROPERTIES_SUFFIC)) {
                Properties properties = PropetiesUtil.loadProperties(s, handleClass);
                env.addElementsByPropreties(properties);
            }
        }
    }

}
