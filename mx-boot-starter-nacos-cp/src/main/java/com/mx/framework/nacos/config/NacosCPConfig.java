package com.mx.framework.nacos.config;
import com.mx.framework.annotation.core.Component;
import com.mx.framework.annotation.core.Value;

/**
 * @Desc 配置信息
 * @Author Zheng.LiMing
 * @Date 2019/9/13
 */
@Component("nacosCPConfig")
public class NacosCPConfig {

    @Value("${nacos.cp.serverAddr:127.0.0.1:8848}")
    private String serverAddr;
    @Value("${nacos.cp.namespace:public}")
    private String namespace;
    @Value("${nacos.cp.group:MACOSX_DEFAULT}")
    private String group;
    @Value("${nacos.cp.server.port:8080}")
    private Integer port;
    @Value("${nacos.cp.clusterName}:DBC")
    private String clusterName;
    @Value("${nacos.cp.serviceName:MACOSX_BOOT}")
    private String serviceName;

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
