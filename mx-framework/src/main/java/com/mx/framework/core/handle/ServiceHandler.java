package com.mx.framework.core.handle;

import com.mx.common.util.StringUtils;
import com.mx.framework.annotation.core.Service;
import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.enums.ScopeType;

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
