package com.duanya.spring.framework.mvc.handler.impl;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyPathVariable;
import com.duanya.spring.framework.annotation.DyRequestBody;
import com.duanya.spring.framework.annotation.DyRequestParameter;
import com.duanya.spring.framework.core.bean.factory.DyAutowiredFactory;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.bean.factory.DyValueFactory;
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
import java.net.URLDecoder;
import java.util.Date;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description
 */
public class DyHandlerExecution implements DyHandlerAdapter {


    @Override
    public Object handle(HttpServletRequest request, HttpServletResponse response, RequestUrlBean handler) throws Exception {

        Properties env=DyConfigurationLoader.getEvn();
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
                    param[index]= setParam(parameter.getType().getSimpleName(),data);
                } else if (parameter.isAnnotationPresent(DyPathVariable.class)) {
                    if (handler.isBringParam()) {
                        String url = StringUtils.formatUrl(request.getRequestURI());
                        String pathParam = url.substring(handler.getRequestUrl().length() - 1);
                        pathParam = URLDecoder.decode(pathParam,"utf-8");
                        param[index] = setParam(parameter.getType().getSimpleName(),pathParam);
                    }
                } else if (parameter.isAnnotationPresent(DyRequestBody.class)) {
                    String json = RequestJosnUtil.getRequestJsonString(request);
                    if (StringUtils.isEmptyPlus(json)) {
                        throw new Exception(request.getRequestURI()+"请求缺少主体，类型为："+parameter.getType().getName());
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
        DyValueFactory.doFields(obj,env);
        DyAutowiredFactory.doAutowired(obj);
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

    public Object setParam(String type,String value) throws Exception {
        Object targer=null;
      switch (type){
          case "Integer":
          case "int":
              targer=Integer.parseInt(value);
              break;
          case "String":
              targer=value;
              break;
          case "Double":
          case "double":
              targer=Double.parseDouble(value);
              break;
          case "Float":
          case "float":
              targer=Float.parseFloat(value);
              break;
          case "Boolean":
          case "boolean":
              targer=Boolean.parseBoolean(value);
              break;
          case "Date":
              targer=Date.parse(value);
              break;
         default:
             throw new Exception("不支持此类型的数据！");
      }
      return targer;
    }

}
