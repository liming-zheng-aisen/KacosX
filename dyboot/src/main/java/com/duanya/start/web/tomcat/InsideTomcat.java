package com.duanya.start.web.tomcat;

import com.duanya.spring.framework.mvc.dispatcher.DyDispatchedServlet;
import com.duanya.start.web.times.Timer;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;

/**
 * @author zhengliming
 */
public class InsideTomcat {

    /**
     * tomcat的端口号
     */
    private static String port = "8888";
    /**
     * tomcat的字符编码集
     */
    private static String code = "UTF-8";
    /**
     * 拦截请求路径
     */
    private static String hinderURL = "/";
    /**
     * 请求转发路径
     */
    private static String shiftURL = "/";
    /**
     * tomcat对象
     */
    private static Tomcat tomcat;
    /**
     * servlet对象
     */
    private static HttpServlet servlet;

    /**
     * 启动这个内嵌tomcat容器
     */
    public static void start(Timer timer,Class main) throws LifecycleException {
        if (null == tomcat) {
            try {
                init(main);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 启动tomcat
        tomcat.start();
        long end = System.currentTimeMillis();
        System.out.println("dy-boot 成功启动，花费时间为" +timer.spendingTime() + "ms");
        // 保持tomcat的启动状态
        tomcat.getServer().await();
    }

    /** 初始化tomcat容器 */
    private static void init(Class main){

        tomcat = new Tomcat();
        // HttpServlet对象是你现有的自定义的Servlet容器
        servlet = new DyDispatchedServlet("application.properties",main);

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


    }

    /**
     * 以下Setters和Getters方法提供外部访问或灵活设置
     */
    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        InsideTomcat.port = port;
    }

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        InsideTomcat.code = code;
    }

    public static String getHinderURL() {
        return hinderURL;
    }

    public static void setHinderURL(String hinderURL) {
        InsideTomcat.hinderURL = hinderURL;
    }

    public static String getShiftURL() {
        return shiftURL;
    }

    public static void setShiftURL(String shiftURL) {
        InsideTomcat.shiftURL = shiftURL;
    }

    public static Tomcat getTomcat() {
        return tomcat;
    }

    public static void setTomcat(Tomcat tomcat) {
        InsideTomcat.tomcat = tomcat;
    }

    public static HttpServlet getServlet() {
        return servlet;
    }

    public static void setServlet(HttpServlet servlet) {
        InsideTomcat.servlet = servlet;
    }
}