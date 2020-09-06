package com.mx.framework.starter.handle;

import com.mx.common.properties.PropertiesType;
import com.mx.common.properties.PropertiesUtil;
import com.mx.framework.annotation.core.AutoConfiguration;
import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.env.ApplicationENV;
import com.mx.framework.core.handle.base.BaseHandler;

import java.util.Map;
import java.util.Properties;

/**
 * @Desc 默认配置
 * @Author Zheng.LiMing
 * @Date 2020/1/14
 */
@SuppressWarnings("all")
public class AutoConfigurationHandler extends BaseHandler {

    public AutoConfigurationHandler() {
        handleAnnotations = new Class[]{
                AutoConfiguration.class
        };
    }

    private final static String[] APPLICATION = new String[]{"application.properties", "application.yml", "application.yaml"};

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {
        if (doBefore(mainClass, handleClass, args)) {
            doLoadSourceToEnv(APPLICATION, handleClass);
        }
        return doAfter(mainClass, handleClass, args);
    }

    public void doLoadSourceToEnv(String[] source, Class handleClass) throws Exception {
        BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null, ApplicationENV.class, true);
        ApplicationENV env = null;
        if (beanDefinition == null) {
            BeanManager.registerClass(ApplicationENV.class);
            beanDefinition = BeanManager.getBeanDefinition(null, ApplicationENV.class, true);
        }
        env = (ApplicationENV) newInstance(beanDefinition,null);
        for (String s : source) {
            if (s.endsWith(PropertiesType.YAML_SUFFIC) || s.endsWith(PropertiesType.YML_SUFFIC)) {
                Map map = PropertiesUtil.loadYaml(s, handleClass);
                env.addElementByMap(map);
                if (map != null) {
                    break;
                }
            } else if (s.endsWith(PropertiesType.PROPERTIES_SUFFIC)) {
                Properties properties = PropertiesUtil.loadProperties(s, handleClass);
                env.addElementsByPropreties(properties);
                if (properties != null) {
                    break;
                }
            }
        }
    }

}
