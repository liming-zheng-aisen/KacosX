package com.duanya.spring.framework.mvc.handler.impl;

import com.duanya.spring.commont.util.StringUtils;
import com.duanya.spring.framework.core.annotation.DyPathVariable;
import com.duanya.spring.framework.core.annotation.DyRequestBody;
import com.duanya.spring.framework.core.annotation.DyRequestParameter;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import com.duanya.spring.framework.mvc.handler.DyHandlerAdapter;
import com.duanya.spring.framework.mvc.handler.bean.RequestUrlBean;
import com.duanya.spring.framework.mvc.util.JsonUtil;
import com.duanya.spring.framework.mvc.util.RequestJosnUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description
 */
public class DyHandlerExecution implements DyHandlerAdapter {

    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, RequestUrlBean handler) throws Exception {

        Object[] param = null;
        if (handler.getMethod().getParameterCount() > 0) {
            param = new Object[handler.getMethod().getParameterCount()];
            int index = 0;
            Parameter[] parameters = handler.getMethod().getParameters();
            for (Parameter parameter : parameters) {
                if (parameter.isAnnotationPresent(DyRequestParameter.class)) {
                    DyRequestParameter requestParameter = parameter.getAnnotation(DyRequestParameter.class);
                    String value = requestParameter.value();
                    if (StringUtils.isEmptyPlus(value)) {
                        value = parameter.getName();
                    }
                    String data = request.getParameter(value);
                    if (StringUtils.isEmptyPlus(data)) {

                        if (requestParameter.request()) {
                            data = requestParameter.defaultValue();
                            if (StringUtils.isEmptyPlus(data)) {
                                throw new Exception("参数缺失！");
                            }
                        }

                    }
                    param[index] = data;
                } else if (parameter.isAnnotationPresent(DyPathVariable.class)) {
                    if (handler.isBringParam()) {
                        String url = StringUtils.formatUrl(request.getRequestURI());
                        String pathParam = url.substring(url.lastIndexOf("/") + 1);
                        param[index] = pathParam;
                    }
                } else if (parameter.isAnnotationPresent(DyRequestBody.class)) {
                    String json = RequestJosnUtil.getRequestJsonString(request);
                    if (StringUtils.isEmptyPlus(json)) {
                        param[index] = null;
                        continue;
                    }
                    if (parameter.getClass().getSimpleName().equals("List")) {
                        param[index] = JsonUtil.jsonToList(json, getActualTypeArgument(parameter.getType()));
                    } else {
                        param[index] = JsonUtil.jsonToBean(json, parameter.getType());
                    }
                } else if (parameter.getClass().getSimpleName().equals("HttpServletRequest")) {
                    param[index] = request;
                } else if (parameter.getClass().getSimpleName().equals("HttpServletResponse")) {
                    param[index] = response;
                } else {
                    param[index] = null;
                }
                index++;
            }
        }
        Object obj=DyBeanFactory.createNewBean(handler.getHandler());
        DyBeanFactory.doFields(obj, DyConfigurationLoader.getEvn());
        DyBeanFactory.doAutowired(obj);
        Object result=handler.getMethod().invoke(obj, param);
        return result;
    }

    /**
     * 获取泛型类Class对象，不是泛型类则返回null
     */
    private Class<?> getActualTypeArgument(Class<?> clazz) {
        Class<?> entitiClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
                    .getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                entitiClass = (Class<?>) actualTypeArguments[0];
            }
        }
        return entitiClass;
    }

}
