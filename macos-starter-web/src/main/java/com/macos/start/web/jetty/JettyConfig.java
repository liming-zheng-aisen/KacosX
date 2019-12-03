package com.macos.start.web.jetty;

import com.macos.framework.annotation.Configuration;
import com.macos.framework.annotation.Value;

/**
 * @Desc TomcatConfig
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */

@Configuration
public class JettyConfig {

    /**
     * Jetty的端口号
     */
    @Value("${dy.server.port}:8099")
    private Integer port;

    @Value("${dy.server.host}:0.0.0.0")
    private String host;

    @Value("${dy.server.static.source.path}:static")
    private String sourcePath;


    @Value("${dy.server.static.source.request.sufix}:.*")
    private String sufix;

    @Value("${dy.server.default.path}:/")
    private String defaultPaht;

    @Value("${dy.server.idleTimeout}:120000")
    private Integer idleTimeout;

    @Value("${dy.server.minThreads}:10")
    private Integer minThreads;

    @Value("${dy.server.maxThreads}:500")
    private Integer maxThreads;

    @Value("${dy.server.ignorePath}: ")
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
