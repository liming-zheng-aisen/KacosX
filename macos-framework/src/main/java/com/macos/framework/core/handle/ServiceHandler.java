package com.macos.framework.core.handle;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Service;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;
import com.macos.framework.enums.ScopeType;

import java.util.Set;

/**
 * @Desc ServiceHandle
 * @Author Zheng.LiMing
 * @Date 2020/1/6
 */
@SuppressWarnings("all")
public class ServiceHandler extends ComponentHandler {

   public ServiceHandler() {
        handleAnnotations = new Class[]{Service.class};
   }

    @Override
    protected void setScope(BeanDefinition beanDefinition){
       Class target = beanDefinition.getTarget();
        String beanName = target.getName();
        Service service = (Service) target.getAnnotation(Service.class);
        if (ScopeType.SINGLETON == service.scope()){
            beanDefinition.setSingleton();
        }else {
            beanDefinition.setPrototype();
        }
    }

    @Override
    protected String getBeanName(Class target) {
        String beanName = target.getName();
        Service service = (Service) target.getAnnotation(Service.class);
        if (StringUtils.isNotEmptyPlus(service.value())) {
            beanName = service.value();
        }
        return beanName;
    }
}
