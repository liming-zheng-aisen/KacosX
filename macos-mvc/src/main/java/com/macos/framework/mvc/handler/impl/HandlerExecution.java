package com.macos.framework.mvc.handler.impl;


import com.macos.common.util.JsonUtil;
import com.macos.common.util.StringUtils;
import com.macos.common.util.TypeUtil;
import com.macos.framework.annotation.PathVariable;
import com.macos.framework.annotation.RequestBody;
import com.macos.framework.annotation.RequestParameter;
import com.macos.framework.core.bean.factory.AutowiredFactory;
import com.macos.framework.core.bean.factory.BeanFactory;
import com.macos.framework.core.bean.factory.ValueFactory;
import com.macos.framework.core.load.conf.ConfigurationLoader;
import com.macos.framework.mvc.handler.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description
 */
public class HandlerExecution implements HandlerAdapter {


    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, com.duanya.spring.framework.mvc.handler.bean.RequestUrlBean handler) throws Exception {

        Properties env= ConfigurationLoader.getEvn();
        Object[] param = null;
        if (handler.getMethod().getParameterCount() > 0) {
            param = new Object[handler.getMethod().getParameterCount()];
            int index = 0;
            Parameter[] parameters = handler.getMethod().getParameters();
            for (Parameter parameter : parameters) {
                if (parameter.isAnnotationPresent(RequestParameter.class)) {
                    RequestParameter requestParameter = parameter.getAnnotation(RequestParameter.class);
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
                    param[index]= TypeUtil.baseType(parameter.getType().getSimpleName(),data);
                } else if (parameter.isAnnotationPresent(PathVariable.class)) {
                    if (handler.isBringParam()) {
                        String url = StringUtils.formatUrl(request.getRequestURI());
                        String pathParam = url.substring(handler.getRequestUrl().length() - 1);
                        pathParam = URLDecoder.decode(pathParam,"utf-8");
                        param[index] = TypeUtil.baseType(parameter.getType().getSimpleName(),pathParam);
                    }
                } else if (parameter.isAnnotationPresent(RequestBody.class)) {
                    String json = com.duanya.spring.framework.mvc.util.RequestJosnUtil.getRequestJsonString(request);
                    if (StringUtils.isEmptyPlus(json)) {
                        throw new Exception(request.getRequestURI()+"请求缺少主体，类型为："+parameter.getType().getName());
                    }
                    if (parameter.getClass().getSimpleName().equals("List")) {
                        param[index] = JsonUtil.jsonToList(json, getActualTypeArgument(parameter.getType()));
                    } else {
                        param[index] = JsonUtil.jsonToBean(json, parameter.getType());
                    }
                } else if (parameter.getType().getSimpleName().equals("HttpServletRequest")) {
                    param[index] = request;
                } else if (parameter.getType().getSimpleName().equals("HttpServletResponse")) {
                    param[index] = response;
                } else {
                    param[index] = null;
                }
                index++;
            }
        }
        Object obj= BeanFactory.createNewBean(handler.getHandler());
        ValueFactory.doFields(obj,env);
        AutowiredFactory.doAutowired(obj,env);
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
