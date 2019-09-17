package com.duanya.start.web.jetty;

import com.duanya.spring.framework.annotation.DyConfiguration;
import com.duanya.spring.framework.annotation.DyValue;

/**
 * @Desc TomcatConfig
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */

@DyConfiguration
public class DyJettyConfig {

    /**
     * Jetty的端口号
     */
    @DyValue("${dy.server.port}:8099")
    private Integer port;

    @DyValue("${dy.server.host}:0.0.0.0")
    private String host;

    @DyValue("${dy.server.static.source.path}:static")
    private String sourcePath;


    @DyValue("${dy.server.static.source.request.sufix}:.*")
    private String sufix;

    @DyValue("${dy.server.default.path}:/")
    private String defaultPaht;

    @DyValue("${dy.server.idleTimeout}:120000")
    private Integer idleTimeout;

    @DyValue("${dy.server.minThreads}:10")
    private Integer minThreads;

    @DyValue("${dy.server.maxThreads}:500")
    private Integer maxThreads;

    @DyValue("${dy.server.ignorePath}: ")
    private String ignorePath;


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getSufix() {
        return sufix;
    }

    public void setSufix(String sufix) {
        this.sufix = sufix;
    }

    public String getDefaultPaht() {
        return defaultPaht;
    }

    public void setDefaultPaht(String defaultPaht) {
        this.defaultPaht = defaultPaht;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Integer getMinThreads() {
        return minThreads;
    }

    public void setMinThreads(Integer minThreads) {
        this.minThreads = minThreads;
    }

    public Integer getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
    }

    public String getIgnorePath() {
        return ignorePath;
    }

    public void setIgnorePath(String ignorePath) {
        this.ignorePath = ignorePath;
    }

    @Override
    public String toString() {
        return "DyJettyConfig{" +
                "port=" + port +
                ", host='" + host + '\'' +
                ", sourcePath='" + sourcePath + '\'' +
                ", sufix='" + sufix + '\'' +
                ", defaultPaht='" + defaultPaht + '\'' +
                ", idleTimeout=" + idleTimeout +
                ", minThreads=" + minThreads +
                ", maxThreads=" + maxThreads +
                ", ignorePath=" + ignorePath +
                '}';
    }
}
