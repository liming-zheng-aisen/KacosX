package com.duanya.spring.framework.nacos.rest;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.utils.JSONUtils;
import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.common.util.TypeUtil;
import com.duanya.spring.framework.context.manager.DyContextManager;
import com.duanya.spring.framework.nacos.balance.DefaultNacosBalanceServiceImpl;
import com.duanya.spring.framework.nacos.balance.DyNacosBalanceSerivce;
import com.duanya.spring.framework.nacos.rest.constant.MediaTypeConst;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.List;

/**
 * @Desc rest客户端
 * @Author Zheng.LiMing
 * @Date 2019/9/13
 */
public class RestClient{

    private  OkHttpClient httpClient=new OkHttpClient();

    private DyNacosBalanceSerivce dyNacosBalanceSerivce;


    public RestClient() {
    }

    public RestClient(DyNacosBalanceSerivce dyNacosBalanceSerivce) {
        this.dyNacosBalanceSerivce = dyNacosBalanceSerivce;
    }

    public DyNacosBalanceSerivce getDyNacosBalanceSerivce() {
        return dyNacosBalanceSerivce;
    }

    public void setDyNacosBalanceSerivce(DyNacosBalanceSerivce dyNacosBalanceSerivce) {
        this.dyNacosBalanceSerivce = dyNacosBalanceSerivce;
    }

    /**
     * 获取一个健康实例，采用nacos负载算法
     * @param serviceName
     * @param groupName
     * @param clusterNames
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws NacosException
     */
    private Instance getHeathyInstance( String serviceName, String groupName, List<String>clusterNames) throws Exception {
        if (StringUtils.isEmptyPlus(serviceName)||StringUtils.isEmptyPlus(groupName)){
            throw  new Exception("serviceName、groupName不允许为空！");
        }
        DyContextManager context=DyContextManager.BuilderContext.getContextManager();
        DyNacosBalanceSerivce namingService=(DyNacosBalanceSerivce)context.getBean("dyNacosBalanceSerivce",DyNacosBalanceSerivce.class);
        if (null==namingService){
            namingService=new DefaultNacosBalanceServiceImpl();
        }
       return namingService.getHealthyInstance(serviceName,groupName,clusterNames);
    }

    private String getNacosUrl(Instance instance,String url){
       return "http://"+instance.getIp()+":"+instance.getPort()+StringUtils.formatUrl(url);
    }

    /**
     * get请求,普通请求
     * @param url
     * @return
     * @throws Exception
     */
    private String requestGet(String url) throws Exception {

        Request request=new Request.Builder().url(url).get().build();

        Response response=httpClient.newCall(request).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }
        throw new Exception("请求失败");
    }

    /**
     * get请求，返回指定的对象
     * @param url
     * @param responseType
     * @return
     * @throws Exception
     */
    public Object GET(String url,Class responseType) throws Exception {
        String result=requestGet(url);
        if (TypeUtil.isBaseType(responseType,true)){
            return TypeUtil.baseType(responseType.getSimpleName(),result);
        }
        return JSONUtils.deserializeObject(url,responseType);
    }

    /**
     * get请求，返回复杂性的对象
     * @param url
     * @param responseType
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T GET(String url,TypeReference<T> responseType) throws Exception {
        String result=requestGet(url);
        return JSONUtils.deserializeObject(result, responseType);
    }

    /**
     * get请求，从nacos获取一个健康服务，并发送get请求
     * @param serviceName
     * @param groupName
     * @param clusterNames
     * @param url
     * @param responseType
     * @return 简单对象
     * @throws Exception
     */
    public Object GET(String serviceName,String groupName,List<String>clusterNames,String url,Class responseType) throws Exception {
        Instance instance=getHeathyInstance(serviceName,groupName,clusterNames);
        String requestUrl=getNacosUrl(instance,url);
        return GET(requestUrl,responseType);
    }

    /**
     * get请求，从nacos获取一个健康服务，并发送get请求
     * @param serviceName
     * @param groupName
     * @param clusterNames
     * @param url
     * @param responseType
     * @param <T>
     * @return 复杂对象
     * @throws Exception
     */
    public  <T> T GET(String serviceName,String groupName,List<String>clusterNames,String url,TypeReference<T> responseType) throws Exception {
        Instance instance=getHeathyInstance(serviceName,groupName,clusterNames);
        String requestUrl=getNacosUrl(instance,url);
        String result=requestGet(requestUrl);
        return JSONUtils.deserializeObject(result, responseType);
    }

    /**
     * post请求
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    private  String requestPost(String url,Object param) throws Exception {
        String data=null;
        if (null!=param){
            if (TypeUtil.isBaseType(param.getClass(),true)){
                data=(String) param;
            }else{
                data=JSONUtils.serializeObject(param);
            }
        }
        RequestBody requestBody=RequestBody.create(MediaTypeConst.JSON,data);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Response response=httpClient.newCall(request).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }
        throw new Exception("请求失败");
    }

    /**
     * post请求
     * @param url
     * @param param
     * @param responseType
     * @return 简单对象
     * @throws Exception
     */
    public Object POST(String url,Object param,Class responseType) throws Exception {
        String result=requestPost(url,param);
        if (TypeUtil.isBaseType(responseType,true)){
            return TypeUtil.baseType(responseType.getSimpleName(),result);
        }
        return JSONUtils.deserializeObject(url,responseType);
    }


    public <T> T POST(String url,Object param,TypeReference<T> responseType) throws Exception {
        String result=requestPost(url,param);
        return JSONUtils.deserializeObject(result, responseType);
    }

    public Object POST(String serviceName,String groupName,List<String>clusterNames,Object param,String url,Class responseType) throws Exception {
        Instance instance=getHeathyInstance(serviceName,groupName,clusterNames);
        String requestUrl=getNacosUrl(instance,url);
        return POST(requestUrl,param,responseType);
    }

    public  <T> T POST(String serviceName,String groupName,List<String>clusterNames,Object param,String url,TypeReference<T> responseType) throws Exception {
        Instance instance=getHeathyInstance(serviceName,groupName,clusterNames);
        String requestUrl=getNacosUrl(instance,url);
        String result=requestPost(requestUrl,param);
        return JSONUtils.deserializeObject(result, responseType);
    }
    private  String requestPut(String url,Object param) throws Exception {
        String data=null;
        if (null!=param){
            if (TypeUtil.isBaseType(param.getClass(),true)){
                data=(String) param;
            }else{
                data=JSONUtils.serializeObject(param);
            }
        }
        RequestBody requestBody=RequestBody.create(MediaTypeConst.JSON,data);
        Request request=new Request.Builder().url(url).put(requestBody).build();
        Response response=httpClient.newCall(request).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }
        throw new Exception("请求失败");
    }

    public Object PUT(String url,Object param,Class responseType) throws Exception {
        String result=requestPut(url,param);
        if (TypeUtil.isBaseType(responseType,true)){
            return TypeUtil.baseType(responseType.getSimpleName(),result);
        }
        return JSONUtils.deserializeObject(url,responseType);
    }

    public <T> T PUT(String url,Object param,TypeReference<T> responseType) throws Exception {
        String result=requestPut(url,param);
        return JSONUtils.deserializeObject(result, responseType);
    }

    public Object PUT(String serviceName,String groupName,List<String>clusterNames,Object param,String url,Class responseType) throws Exception {
        Instance instance=getHeathyInstance(serviceName,groupName,clusterNames);
        String requestUrl=getNacosUrl(instance,url);
        return PUT(requestUrl,param,responseType);
    }

    public  <T> T PUT(String serviceName,String groupName,List<String>clusterNames,Object param,String url,TypeReference<T> responseType) throws Exception {
        Instance instance=getHeathyInstance(serviceName,groupName,clusterNames);
        String requestUrl=getNacosUrl(instance,url);
        String result=requestPut(requestUrl,param);
        return JSONUtils.deserializeObject(result, responseType);
    }

    private  String requestDelete(String url,Object param) throws Exception {
        String data=null;
        if (null!=param){
            if (TypeUtil.isBaseType(param.getClass(),true)){
                data=(String) param;
            }else{
                data=JSONUtils.serializeObject(param);
            }
        }
        RequestBody requestBody=RequestBody.create(MediaTypeConst.JSON,data);
        Request request=new Request.Builder().url(url).delete(requestBody).build();
        Response response=httpClient.newCall(request).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }
        throw new Exception("请求失败");
    }

    public Object DELETE(String url,Object param,Class responseType) throws Exception {
        String result=requestDelete(url,param);
        if (TypeUtil.isBaseType(responseType,true)){
            return TypeUtil.baseType(responseType.getSimpleName(),result);
        }
        return JSONUtils.deserializeObject(url,responseType);
    }

    public <T> T DELETE(String url,Object param,TypeReference<T> responseType) throws Exception {
        String result=requestDelete(url,param);
        return JSONUtils.deserializeObject(result, responseType);
    }

    public Object DELETE(String serviceName,String groupName,List<String>clusterNames,Object param,String url,Class responseType) throws Exception {
        Instance instance=getHeathyInstance(serviceName,groupName,clusterNames);
        String requestUrl=getNacosUrl(instance,url);
        return DELETE(requestUrl,param,responseType);
    }

    public  <T> T DELETE(String serviceName,String groupName,List<String>clusterNames,Object param,String url,TypeReference<T> responseType) throws Exception {
        Instance instance=getHeathyInstance(serviceName,groupName,clusterNames);
        String requestUrl=getNacosUrl(instance,url);
        String result=requestDelete(requestUrl,param);
        return JSONUtils.deserializeObject(result, responseType);
    }

    /**
     *自定义构建Request对象，向nacos服务获取一个健康对象，并请求
     * @param serviceName
     * @param groupName
     * @param clusterNames
     * @param url
     * @param request
     * @return 字符串
     * @throws Exception
     */
    public String run(String serviceName,String groupName,List<String>clusterNames,String url,Request request) throws Exception {
        Instance instance=getHeathyInstance(serviceName,groupName,clusterNames);
        String requestUrl=getNacosUrl(instance,url);
        Response response = httpClient.newCall(request).execute();
        if (response.isSuccessful()){
            return response.body().string();
        }
        throw new Exception("请求失败");
    }

    /**
     * 自定义构建Request对象，向nacos服务获取一个健康对象，并请求
     * @param serviceName
     * @param groupName
     * @param clusterNames
     * @param url
     * @param responseType
     * @param request
     * @return 简单对象
     * @throws Exception
     */
    public Object run(String serviceName,String groupName,List<String>clusterNames,String url,Class responseType,Request request) throws Exception {
        String result=run(serviceName,groupName,clusterNames,url,request);
        if (TypeUtil.isBaseType(responseType,true)){
            return TypeUtil.baseType(responseType.getSimpleName(),result);
        }
        return JSONUtils.deserializeObject(result,responseType);
    }

    /**
     * 自定义构建Request对象，向nacos服务获取一个健康对象，并请求
     * @param serviceName
     * @param groupName
     * @param clusterNames
     * @param url
     * @param responseType
     * @param request
     * @param <T>
     * @return 复杂对象
     * @throws Exception
     */
    public  <T> T run(String serviceName,String groupName,List<String>clusterNames,String url,TypeReference<T> responseType,Request request) throws Exception {
        String result=run(serviceName,groupName,clusterNames,url,request);
        return JSONUtils.deserializeObject(result, responseType);
    }
}