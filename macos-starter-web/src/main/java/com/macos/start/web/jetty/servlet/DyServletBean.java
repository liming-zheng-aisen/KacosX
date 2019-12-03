package com.macos.start.web.jetty.servlet;

import javax.servlet.Servlet;

/**
 * @Desc DyServletBean
 * @Author Zheng.LiMing
 * @Date 2019/9/7
 */

public class DyServletBean {

    private Servlet servlet;

    private String url;

    public Servlet getServlet() {
        return servlet;
    }

    public void setServlet(Servlet servlet) {
        this.servlet = servlet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DyServletBean(){

    }

    public DyServletBean(Servlet servlet, String url) {
        this.servlet = servlet;
        this.url = url;
    }
}
