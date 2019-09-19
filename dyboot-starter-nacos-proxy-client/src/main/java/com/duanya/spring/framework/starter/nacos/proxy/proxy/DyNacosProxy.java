package com.duanya.spring.framework.starter.nacos.proxy.proxy;

import com.duanya.spring.common.util.JsonUtil;
import com.duanya.spring.common.util.TypeUtil;
import com.duanya.spring.framework.starter.nacos.proxy.handle.DyNacosHandle;
import com.duanya.spring.framework.starter.nacos.proxy.handle.bean.DyHandleBeanInfo;
import com.duanya.spring.framework.starter.nacos.proxy.mapping.DyNacosMapping;
import com.duanya.spring.framework.nacos.rest.RestClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * @Desc DyNacosProxy
 * @Author Zheng.LiMing
 * @Date 2019/9/16
 */
public class DyNacosProxy implements InvocationHandler {

    private RestClient restClient;

    public DyNacosProxy(RestClient restClient){
        this.restClient=restClient;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       DyHandleBeanInfo info = DyNacosMapping.handleUrl(method);
       DyNacosMapping.initPathValut(info,method,args);
       String reuslt= (String) DyNacosHandle.execu(restClient,info);
       Class returnType=method.getReturnType();
       if (TypeUtil.isBaseType(returnType,true)){
           return TypeUtil.baseType(returnType.getSimpleName(),reuslt);
       }else{
           ParameterizedType type = (ParameterizedType)returnType.getGenericSuperclass();
           Class cl=type.getActualTypeArguments()[0].getClass();
           if (returnType==List.class){
               return JsonUtil.jsonToList(reuslt,cl);
           }else if (returnType.getSuperclass()==Object.class){
               return JsonUtil.jsonToBean(reuslt,cl);
           }else if (returnType==Map.class){
               return JsonUtil.jsonToMap(reuslt,cl, type.getActualTypeArguments()[1].getClass());
           }
       }
       return reuslt;
    }
}
