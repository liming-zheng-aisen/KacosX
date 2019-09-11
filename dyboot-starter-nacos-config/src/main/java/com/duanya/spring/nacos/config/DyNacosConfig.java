package com.duanya.spring.nacos.config;

import com.duanya.spring.framework.annotation.DyConfiguration;
import com.duanya.spring.framework.annotation.DyValue;

/**
 * @Desc DyNacosConfig
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
@DyConfiguration
public class DyNacosConfig {

    @DyValue("${dy.nacos.config.serverAddr}:127.0.0.1:8848")
    private String serverAddr;

    @DyValue("${dy.nacos.config.dataId}:example.properties")
    private String dataId;

    @DyValue("${dy.nacos.config.group}:DEFAULT_GROUP")
    private String group;

    @DyValue("${dy.nacos.config.timeoutMs}:5000")
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
