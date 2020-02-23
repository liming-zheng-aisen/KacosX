package com.macos.framework.mvc.handler.mvc;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.RestAPI;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.handle.ComponentHandler;
import com.macos.framework.enums.ScopeType;


/**
 * @Desc RestApiHandler
 * @Author Zheng.LiMing
 * @Date 2020/2/14
 */
public class RestApiHandler extends ComponentHandler {

    public RestApiHandler() {
        handleAnnotations = new Class[]{RestAPI.class};
    }

    @Override
    protected void setScope(BeanDefinition beanDefinition) {
        Class target = beanDefinition.getTarget();
        RestAPI restAPI = (RestAPI) target.getAnnotation(RestAPI.class);
        if (ScopeType.SINGLETON == restAPI.scope()) {
            beanDefinition.setSingleton();
        } else {
            beanDefinition.setPrototype();
        }
    }

    @Override
    protected String getBeanName(Class target) {
        String beanName = target.getName();
        RestAPI restAPI = (RestAPI) target.getAnnotation(RestAPI.class);
        if (StringUtils.isNotEmptyPlus(restAPI.value())) {
            beanName = restAPI.value();
        }
        return beanName;
    }

}
