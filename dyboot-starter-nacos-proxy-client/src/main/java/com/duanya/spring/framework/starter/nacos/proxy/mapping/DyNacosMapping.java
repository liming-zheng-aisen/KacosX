package com.duanya.spring.framework.starter.nacos.proxy.mapping;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.*;
import com.duanya.spring.framework.mvc.enums.DyMethod;
import com.duanya.spring.framework.starter.nacos.proxy.handle.bean.DyHandleBeanInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * @Desc DyNacosMapping
 * @Author Zheng.LiMing
 * @Date 2019/9/18
 */
public class DyNacosMapping {

    public static DyHandleBeanInfo handleUrl(Method method){
        DyHandleBeanInfo handleBeanInfo=new DyHandleBeanInfo();

         Class cl=method.getDeclaringClass();
        if (cl.isAnnotationPresent(DyNacosServiceClient.class)){
            DyNacosServiceClient nacosSerivceClient=(DyNacosServiceClient)cl.getAnnotation(DyNacosServiceClient.class);
            String serviceName=nacosSerivceClient.servieName();
            String groupName=nacosSerivceClient.groupName();
            String[] clusterNames=nacosSerivceClient.clusterNames();

            handleBeanInfo.setServiceName(serviceName);
            handleBeanInfo.setGruopName(groupName);
            if (null==clusterNames||clusterNames.length==0){
                handleBeanInfo.setClusterNames(null);
            }else {
                handleBeanInfo.setClusterNames(Arrays.asList(clusterNames));
            }
        }

        String url="";
        if (cl.isAnnotationPresent(DyRequestMapping.class)){
            DyRequestMapping dyRequestMapping= (DyRequestMapping)cl.getAnnotation(DyRequestMapping.class);
            url=dyRequestMapping.value();
        }
        if (method.isAnnotationPresent(DyGet.class)){
            DyGet get=method.getAnnotation(DyGet.class);
            url=url+get.value();
            handleBeanInfo.setMethod(DyMethod.GET);
        }else if (method.isAnnotationPresent(DyPost.class)){
            DyPost post=method.getAnnotation(DyPost.class);
            url=url+post.value();
            handleBeanInfo.setMethod(DyMethod.POST);
        }else if (method.isAnnotationPresent(DyPut.class)){
            DyPut put=method.getAnnotation(DyPut.class);
            url=url+put.value();
            handleBeanInfo.setMethod(DyMethod.PUT);
        }else if (method.isAnnotationPresent(DyDelete.class)){
            DyDelete delete=method.getAnnotation(DyDelete.class);
            url=url+delete.value();
            handleBeanInfo.setMethod(DyMethod.DELETE);
        }else if(method.isAnnotationPresent(DyRequestMapping.class)){
            DyRequestMapping requestMapping=method.getAnnotation(DyRequestMapping.class);
            url=url+requestMapping.value();
            handleBeanInfo.setMethod(requestMapping.method());
        }
        handleBeanInfo.setUrl(StringUtils.formatUrl(url));
        return handleBeanInfo;
    }

    public static DyHandleBeanInfo initPathValut(DyHandleBeanInfo info,Method method,Object[] object){
        Parameter[] parameters = method.getParameters();
        String url=info.getUrl();
        for (int i=0;i<parameters.length;i++){
            if (parameters[i].isAnnotationPresent(DyPathVariable.class)){
                //附带/{}的处理
                String regex=".*\\/\\{.*\\}.*";
                if (url.matches(regex)){
                   url= url.substring(0,url.lastIndexOf("/")+1);
                   url=url+object[i];
                }
            }else if (parameters[i].isAnnotationPresent(DyRequestParameter.class)){
                if (url.lastIndexOf("?")<0){
                    url=url+"?";
                }
                DyRequestParameter requestParameter=parameters[i].getAnnotation(DyRequestParameter.class);
                url= url + requestParameter.value()+"="+object[i]+"&";
            }else if ((parameters[i].isAnnotationPresent(DyRequestBody.class))){
                info.setData(object[i]);
            }
        }
        if (url.indexOf("&")>0){
            url=url.substring(0,url.lastIndexOf("&"));
        }
        info.setUrl(url);
        return info;
    }

}
