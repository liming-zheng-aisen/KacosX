package com.macos.framework.mvc.handler.mvc;

import com.macos.common.util.ReflectionsUtil;
import com.macos.framework.annotation.Post;
import com.macos.framework.annotation.RequestMapping;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;
import com.macos.framework.mvc.util.HttpServerUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Desc RequestMappingHandler
 * @Author Zheng.LiMing
 * @Date 2020/2/14
 */
public class RequestMappingHandler extends BaseHandler {

    public RequestMappingHandler() {
        handleAnnotations = new Class[]{RequestMapping.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (doBefore(mainClass,handleClass,args)){
            BeanDefinition target = BeanManager.getBeanDefinition(null,handleClass,true);
            if (target == null){
                return true;
            }
            /**处理类上面RequestMapping注解*/
            if (needToHandle(handleClass)) {
               RequestMapping requestMapping = (RequestMapping) handleClass.getAnnotation(RequestMapping.class);
               target.setRequestPath(requestMapping.value());
            }
            /**处理方法上的RequestMapping注解*/
            List<Method> methods = ReflectionsUtil.getMethodsByAnnotation(handleClass,RequestMapping.class);
            String path = target.getRequestPath();

            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)){
                    continue;
                }
                RequestMapping requestMapping=method.getDeclaredAnnotation(RequestMapping.class);
                String requestUrl = path + requestMapping.value();
                HttpServerUtil.createBeanAndAddServletContext(handleClass,method,requestUrl,requestMapping.method());
            }

        }
        return doAfter(mainClass,handleClass,args);
    }
}
