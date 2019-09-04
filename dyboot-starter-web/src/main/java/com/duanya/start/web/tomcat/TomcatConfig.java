package com.duanya.start.web.tomcat;

import com.duanya.spring.common.times.DyTimer;
import com.duanya.spring.framework.annotation.DyAutowired;
import com.duanya.spring.framework.annotation.DyBean;
import com.duanya.spring.framework.annotation.DyConfiguration;
import com.duanya.spring.framework.annotation.DyValue;
import org.apache.catalina.Host;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;

/**
 * @Desc TomcatConfig
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */

@DyConfiguration
public class TomcatConfig {

    @DyAutowired
    private DyTimer timer;
    /**
     * servlet对象
     */
    @DyAutowired("dyDispatchedServlet")
    private HttpServlet servlet;
    /**
     * tomcat的端口号
     */
    @DyValue("${dy.server.port}:8080")
    private  String port;
    /**
     * tomcat的字符编码集
     */
    @DyValue("${dy.server.code}:UTF-8")
    private  String code;
    /**
     * 拦截请求路径
     */
    @DyValue("${dy.server.hinderURL}:/")
    private  String hinderURL;
    /**
     * 请求转发路径
     */
    @DyValue("${dy.server.shiftURL}:/")
    private  String shiftURL ;

    @DyBean("tomcat")
    public Tomcat initTomcat(){

        Tomcat tomcat=new Tomcat();
        // 创建连接器
        Connector conn = tomcat.getConnector();
        conn.setPort(Integer.valueOf(port));
        conn.setURIEncoding(code);

        // 设置Host
        Host host = tomcat.getHost();

        host.setAppBase("webapps");

        // 获取目录绝对路径
        String classPath = System.getProperty("user.dir");

        // 配置tomcat上下文
        tomcat.addContext(host, hinderURL, classPath);

        // 配置请求拦截转发
        Wrapper wrapper = tomcat.addServlet(shiftURL, "servlet", servlet);
        wrapper.addMapping(shiftURL);
        return  tomcat;
    }
}
