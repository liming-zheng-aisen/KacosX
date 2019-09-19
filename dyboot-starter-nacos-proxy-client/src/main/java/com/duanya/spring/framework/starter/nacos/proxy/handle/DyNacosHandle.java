package com.duanya.spring.framework.starter.nacos.proxy.handle;

import com.duanya.spring.framework.starter.nacos.proxy.handle.bean.DyHandleBeanInfo;
import com.duanya.spring.framework.nacos.rest.RestClient;

/**
 * @Desc DyNacosHandle
 * @Author Zheng.LiMing
 * @Date 2019/9/18
 */
public class DyNacosHandle {

    public static Object execu( RestClient restClient, DyHandleBeanInfo info) throws Exception {
        if ( null==restClient || null==info ){
            return null;
        }
        switch (info.getMethod()){
            case GET:
                return restClient.GET(info.getServiceName(),info.getGruopName(),info.getClusterNames(),info.getUrl(),String.class);
            case POST:
                return restClient.POST(info.getServiceName(),info.getGruopName(),info.getClusterNames(),info.getData(),info.getUrl(),String.class);
            case PUT:
                return restClient.PUT(info.getServiceName(),info.getGruopName(),info.getClusterNames(),info.getData(),info.getUrl(),String.class);
            case DELETE:
                return restClient.DELETE(info.getServiceName(),info.getGruopName(),info.getClusterNames(),info.getData(),info.getUrl(),String.class);
                default:
                    throw  new Exception("暂时不支持"+info.getMethod()+"请求");
        }
    }
}
