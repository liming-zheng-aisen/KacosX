package com.macos.framework.starter.nacos.handle;


import com.macos.framework.nacos.rest.RestClient;
import com.macos.framework.starter.nacos.handle.bean.HandleBeanInfo;

/**
 * @Desc DyNacosHandle
 * @Author Zheng.LiMing
 * @Date 2019/9/18
 */
public class NacosHandle {

    public static Object execu(RestClient restClient, HandleBeanInfo info) throws Exception {
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
