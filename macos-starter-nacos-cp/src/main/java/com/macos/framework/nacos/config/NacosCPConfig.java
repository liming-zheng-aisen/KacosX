package com.macos.framework.nacos.config;
import com.macos.framework.annotation.Component;
import com.macos.framework.annotation.Value;

/**
 * @Desc 配置信息
 * @Author Zheng.LiMing
 * @Date 2019/9/13
 */
@Component("dyNacosCPConfig")
public class NacosCPConfig {

    @Value("${dy.nacos.cp.serverAddr}:127.0.0.1:8848")
    private String serverAddr;
    @Value("${dy.nacos.cp.namespace}:public")
    private String namespace;
    @Value("${dy.nacos.cp.group}:DYSPRING_DEFAULT")
    private String group;
    @Value("${dy.server.port}:8080")
    private Integer port;
    @Value("${dy.nacos.cp.clusterName}:DBC")
    private String clusterName;
    @Value("${dy.nacos.cp.serviceName}:SERVICE_DYBOOT")
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
