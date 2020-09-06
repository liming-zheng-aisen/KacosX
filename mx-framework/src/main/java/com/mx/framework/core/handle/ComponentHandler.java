package com.mx.framework.core.handle;
import com.mx.common.util.StringUtils;
import com.mx.framework.annotation.core.Component;
import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.handle.base.BaseHandler;
import com.mx.framework.enums.ScopeType;

/**
 * @Desc 默认创建对象
 * @Author Zheng.LiMing
 * @Date 2020/1/6
 */
@SuppressWarnings("all")
public class ComponentHandler extends BaseHandler {

    public ComponentHandler() {
        handleAnnotations = new Class[]{Component.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (!needToHandle(handleClass)) {
            return true;
        }

        BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null,handleClass,true);
        if (beanDefinition != null) {
                Class currentHandleClass = beanDefinition.getTarget();
                //执行前置处理
                if (doBefore(mainClass, currentHandleClass, args)) {
                    //创建并注册当前实例
                    newInstance(beanDefinition, getBeanName(currentHandleClass));
                    setScope(beanDefinition);
                }
                //执行后置处理
                return doAfter(mainClass, currentHandleClass, args);
        }
        return true;
    }
    protected void setScope(BeanDefinition beanDefinition){
        Class target = beanDefinition.getTarget();
        String beanName = target.getName();
        Component component = (Component) target.getAnnotation(Component.class);
        if (ScopeType.SINGLETON == component.scope()){
            beanDefinition.setSingleton();
        }else {
            beanDefinition.setPrototype();
        }
    }

    protected String getBeanName(Class target) {
        String beanName = target.getName();
        Component component = (Component) target.getAnnotation(Component.class);
        if (StringUtils.isNotEmptyPlus(component.value())) {
            beanName = component.value();
        }
        return beanName;
    }
}

