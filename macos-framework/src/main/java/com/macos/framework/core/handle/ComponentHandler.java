package com.macos.framework.core.handle;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Component;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;
import java.util.Set;

/**
 * @Desc ComponentHandle
 * @Author Zheng.LiMing
 * @Date 2020/1/6
 */
@SuppressWarnings("all")
public class ComponentHandler extends BaseHandler {

    static {
        handleAnnotations = new Class[]{Component.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {
        Set<BeanDefinition> classContainer = BeanManager.getBeanDefinitionsByAnnotation(handleAnnotations);
        for (BeanDefinition beanDefinition : classContainer){
            Class currentHandleClass = beanDefinition.getTarget();
            //执行前置处理
            doBefore(mainClass,currentHandleClass, args);
            //创建并注册当前实例
            newInstance(beanDefinition,getBeanName(currentHandleClass));
            //执行后置处理
            doAfter(mainClass,currentHandleClass, args);
        }
        return true;
    }

    private String getBeanName(Class target) {
        String beanName = target.getName();
        Component component = (Component) target.getAnnotation(Component.class);
        if (StringUtils.isNotEmptyPlus(component.value())) {
            beanName = component.value();
        }
        return beanName;
    }
}

