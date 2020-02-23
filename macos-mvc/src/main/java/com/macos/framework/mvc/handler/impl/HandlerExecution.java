package com.macos.framework.mvc.handler.impl;

import com.macos.common.util.JsonUtil;
import com.macos.common.util.ReflectionsUtil;
import com.macos.common.util.StringUtils;
import com.macos.common.util.TypeUtil;
import com.macos.framework.annotation.PathVariable;
import com.macos.framework.annotation.RequestBody;
import com.macos.framework.annotation.RequestParameter;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.mvc.handler.adapter.HandlerAdapter;
import com.macos.framework.mvc.handler.bean.RequestUrlBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description
 */
public class HandlerExecution implements HandlerAdapter {

    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, RequestUrlBean handler) throws Exception {

        Object[] param = null;
        String url = StringUtils.formatUrl(request.getRequestURI());
        String[] urls = url.split("\\/");
        Map<String,String> paramValue = null;
        if (handler.isPathRequest()){
            paramValue = initPathValue(urls,handler.getRequestUrl());
        }
        if (handler.getHandlerMethod().getParameterCount() > 0) {
            param = new Object[handler.getHandlerMethod().getParameterCount()];
            int index = 0;
            Parameter[] parameters = handler.getHandlerMethod().getParameters();
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
                    if (handler.isPathRequest()) {
                        PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
                        String paramName = pathVariable.value();
                        String pValue = null;
                        if (StringUtils.isEmptyPlus(paramName) && paramValue.size()>0){
                           if (paramValue.size()>0) {
                               pValue = (String) paramValue.values().toArray()[0];
                           }
                        }else{
                            pValue = paramValue.get(paramName);
                        }
                        param[index] = TypeUtil.baseType(parameter.getType().getSimpleName(),pValue);
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
        Object obj= BeanManager.getBeanDefinition(null,handler.getHandler(),true).getBean(null,handler.getHandler());
        Object result=handler.getHandlerMethod().invoke(obj, param);
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

    private Map<String,String> initPathValue(String[] target,String[] source) throws UnsupportedEncodingException {
        Map<String,String> values = new HashMap<>();
        for (int i =0 ; i < source.length ; i++){
            if (i>=target.length){
                values.put(formatPathParam(source[i]),null);
                continue;
            }
            if (source[i].contains("{") && source[i].contains("}")){
                values.put(formatPathParam(formatPathParam(source[i])),URLDecoder.decode(target[i],"utf-8"));
            }
        }
        return values;
    }

    private String formatPathParam(String paramName){
        paramName = paramName.replace("{","");
        paramName = paramName.replace("}","");
        return paramName;
    }
}
