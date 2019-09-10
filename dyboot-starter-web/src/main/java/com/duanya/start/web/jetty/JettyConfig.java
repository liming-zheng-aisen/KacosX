package com.duanya.start.web.jetty;

import com.duanya.spring.framework.annotation.DyConfiguration;
import com.duanya.spring.framework.annotation.DyValue;

/**
 * @Desc TomcatConfig
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */

@DyConfiguration
public class JettyConfig {

    /**
     * Jetty的端口号
     */
    @DyValue("${dy.server.port}:8099")
    private Integer port;

    @DyValue("${dy.server.static.source.path}:static")
    private String sourcePath;

    @DyValue("${dy.server.static.source.request.prefix}:/")
    private String prefix;

    @DyValue("${dy.server.static.source.request.sufix}:*")
    private String sufix;

    @DyValue("${dy.server.default.path}:/")
    private String defaultPaht;


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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
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
}
