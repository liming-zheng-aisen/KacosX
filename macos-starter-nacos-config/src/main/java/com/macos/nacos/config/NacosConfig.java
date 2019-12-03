package com.macos.nacos.config;

import com.macos.framework.annotation.Configuration;
import com.macos.framework.annotation.Value;

/**
 * @Desc DyNacosConfig
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
@Configuration
public class NacosConfig {

    @Value("${dy.nacos.config.serverAddr}:127.0.0.1:8848")
    private String serverAddr;

    @Value("${dy.nacos.config.dataId}:example.properties")
    private String dataId;

    @Value("${dy.nacos.config.group}:DEFAULT_GROUP")
    private String group;

    @Value("${dy.nacos.config.timeoutMs}:5000")
    private Integer timeoutMs;

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(Integer timeoutMs) {
        this.timeoutMs = timeoutMs;
    }
}
