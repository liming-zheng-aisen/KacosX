package com.macos.framework.starter.nacos.mapping;


import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.*;
import com.macos.framework.mvc.enums.HttpMethod;
import com.macos.framework.starter.nacos.handle.bean.HandleBeanInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * @Desc DyNacosMapping
 * @Author Zheng.LiMing
 * @Date 2019/9/18
 */
public class NacosMapping {

    public static HandleBeanInfo handleUrl(Method method){
        HandleBeanInfo handleBeanInfo=new HandleBeanInfo();

         Class cl=method.getDeclaringClass();
        if (cl.isAnnotationPresent(NacosServiceClient.class)){
            NacosServiceClient nacosSerivceClient=(NacosServiceClient)cl.getAnnotation(NacosServiceClient.class);
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
        if (cl.isAnnotationPresent(RequestMapping.class)){
            RequestMapping dyRequestMapping= (RequestMapping)cl.getAnnotation(RequestMapping.class);
            url=dyRequestMapping.value();
        }
        if (method.isAnnotationPresent(Get.class)){
            Get get=method.getAnnotation(Get.class);
            url=url+get.value();
            handleBeanInfo.setMethod(HttpMethod.GET);
        }else if (method.isAnnotationPresent(Post.class)){
            Post post=method.getAnnotation(Post.class);
            url=url+post.value();
            handleBeanInfo.setMethod(HttpMethod.POST);
        }else if (method.isAnnotationPresent(Put.class)){
            Put put=method.getAnnotation(Put.class);
            url=url+put.value();
            handleBeanInfo.setMethod(HttpMethod.PUT);
        }else if (method.isAnnotationPresent(Delete.class)){
            Delete delete=method.getAnnotation(Delete.class);
            url=url+delete.value();
            handleBeanInfo.setMethod(HttpMethod.DELETE);
        }else if(method.isAnnotationPresent(RequestMapping.class)){
            RequestMapping requestMapping=method.getAnnotation(RequestMapping.class);
            url=url+requestMapping.value();
            handleBeanInfo.setMethod(requestMapping.method());
        }
        handleBeanInfo.setUrl(StringUtils.formatUrl(url));
        return handleBeanInfo;
    }

    public static HandleBeanInfo initPathValut(HandleBeanInfo info,Method method,Object[] object){
        Parameter[] parameters = method.getParameters();
        String url=info.getUrl();
        for (int i=0;i<parameters.length;i++){
            if (parameters[i].isAnnotationPresent(PathVariable.class)){
                //附带/{}的处理
                String regex=".*\\/\\{.*\\}.*";
                if (url.matches(regex)){
                   url= url.substring(0,url.lastIndexOf("/")+1);
                   url=url+object[i];
                }
            }else if (parameters[i].isAnnotationPresent(RequestParameter.class)){
                if (url.lastIndexOf("?")<0){
                    url=url+"?";
                }
                RequestParameter requestParameter=parameters[i].getAnnotation(RequestParameter.class);
                url= url + requestParameter.value()+"="+object[i]+"&";
            }else if ((parameters[i].isAnnotationPresent(RequestBody.class))){
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
