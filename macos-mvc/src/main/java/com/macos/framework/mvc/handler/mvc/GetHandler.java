package com.macos.framework.mvc.handler.mvc;

import com.macos.common.util.ReflectionsUtil;
import com.macos.framework.annotation.Get;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;
import com.macos.framework.enums.HttpMethod;
import com.macos.framework.mvc.util.HttpServerUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Desc GetHandler
 * @Author Zheng.LiMing
 * @Date 2020/2/14
 */
public class GetHandler extends BaseHandler {

    public GetHandler() {
        handleAnnotations = new Class[]{Get.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (doBefore(mainClass,handleClass,args)){
            BeanDefinition target = BeanManager.getBeanDefinition(null,handleClass,true);

            List<Method> methods = ReflectionsUtil.getMethodsByAnnotation(handleClass,Get.class);

            if (target == null || methods.size()==0){
                return true;
            }
            String path = target.getRequestPath();

            for (Method method : methods) {
                if (!method.isAnnotationPresent(Get.class)){
                    continue;
                }
                Get get = method.getAnnotation(Get.class);
                String requestUrl = path + get.value();
                HttpServerUtil.createBeanAndAddServletContext(handleClass,method,requestUrl,HttpMethod.GET);
            }

        }
        return doAfter(mainClass,handleClass,args);
    }
}
