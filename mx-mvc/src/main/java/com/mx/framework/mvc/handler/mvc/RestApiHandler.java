package com.mx.framework.mvc.handler.mvc;

import com.mx.common.util.StringUtils;
import com.mx.framework.annotation.http.RestAPI;
import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.core.handle.ComponentHandler;
import com.mx.framework.enums.ScopeType;


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
