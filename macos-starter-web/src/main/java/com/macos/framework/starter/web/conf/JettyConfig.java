package com.macos.framework.starter.web.conf;

import com.macos.framework.annotation.Configuration;
import com.macos.framework.annotation.Value;

/**
 * @Desc JettyConfig
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */

@Configuration
public class JettyConfig {

    /**
     * Jetty的端口号
     */
    @Value("${server.port:8080}")
    private Integer port;

    @Value("${server.host:0.0.0.0}")
    private String host;

    @Value("${server.static.source.path:static}")
    private String sourcePath;


    @Value("${server.static.source.request.sufix:.*}")
    private String sufix;

    @Value("${server.default.path:/}")
    private String defaultPaht;

    @Value("${server.idleTimeout:120000}")
    private Integer idleTimeout;

    @Value("${server.minThreads:10}")
    private Integer minThreads;

    @Value("${server.maxThreads:500}")
    private Integer maxThreads;

    @Value("${server.ignorePath: }")
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
