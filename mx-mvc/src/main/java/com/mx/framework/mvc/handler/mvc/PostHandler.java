package com.mx.framework.mvc.handler.mvc;

import com.mx.common.util.ReflectionsUtil;
import com.mx.framework.annotation.http.Post;
import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.handle.base.BaseHandler;
import com.mx.framework.enums.HttpMethod;
import com.mx.framework.mvc.util.HttpServerUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Desc PostHandler
 * @Author Zheng.LiMing
 * @Date 2020/2/14
 */
public class PostHandler extends BaseHandler {

    public PostHandler() {
        handleAnnotations = new Class[]{Post.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (doBefore(mainClass,handleClass,args)){
            BeanDefinition target = BeanManager.getBeanDefinition(null,handleClass,true);

            List<Method> methods = ReflectionsUtil.getMethodsByAnnotation(handleClass,Post.class);

            if (target == null || methods.size()==0){
                return true;
            }
            String path = target.getRequestPath();

            for (Method method : methods) {
                if (!method.isAnnotationPresent(Post.class)){
                    continue;
                }
                Post post = method.getAnnotation(Post.class);
                String requestUrl = path + post.value();
                HttpServerUtil.createBeanAndAddServletContext(handleClass,method,requestUrl,HttpMethod.POST);
            }

        }
        return doAfter(mainClass,handleClass,args);
    }
}
