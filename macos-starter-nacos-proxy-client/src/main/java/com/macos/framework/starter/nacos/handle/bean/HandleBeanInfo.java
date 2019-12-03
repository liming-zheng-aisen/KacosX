package com.macos.framework.starter.nacos.handle.bean;

import com.macos.framework.mvc.enums.HttpMethod;

import java.util.List;

/**
 * @Desc DyHandleBeanInfo
 * @Author Zheng.LiMing
 * @Date 2019/9/18
 */
public class HandleBeanInfo {
    private String url;
    private String serviceName;
    private String gruopName;
    private List<String> clusterNames;
    private HttpMethod method;
    private Object data;
    private Class responceType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getGruopName() {
        return gruopName;
    }

    public void setGruopName(String gruopName) {
        this.gruopName = gruopName;
    }

    public List<String> getClusterNames() {
        return clusterNames;
    }

    public void setClusterNames(List<String> clusterNames) {
        this.clusterNames = clusterNames;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Class getResponceType() {
        return responceType;
    }

    public void setResponceType(Class responceType) {
        this.responceType = responceType;
    }
}
