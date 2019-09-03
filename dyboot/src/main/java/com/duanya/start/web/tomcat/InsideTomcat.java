package com.duanya.start.web.tomcat;

import com.duanya.spring.commont.util.StringUtils;
import com.duanya.spring.framework.mvc.dispatcher.DyDispatchedServlet;
import com.duanya.start.web.times.Timer;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import java.util.Properties;

/**
 * @author zhengliming
 */
public class InsideTomcat {

    private static final Logger log=LoggerFactory.getLogger(InsideTomcat.class);

    /**
     * tomcat的端口号
     */
    private  String port = "8888";
    /**
     * tomcat的字符编码集
     */
    private  String code = "UTF-8";
    /**
     * 拦截请求路径
     */
    private  String hinderURL = "/";
    /**
     * 请求转发路径
     */
    private  String shiftURL = "/";
    /**
     * tomcat对象
     */
    private  static Tomcat tomcat;
    /**
     * servlet对象
     */
    private  HttpServlet servlet;

    /**
     * 启动这个内嵌tomcat容器
     */
    public  void start(Timer timer,Class main) throws LifecycleException {
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
        log.info("dy-boot 成功启动，花费时间为{}ms",timer.spendingTime());
        // 保持tomcat的启动状态
        tomcat.getServer().await();
    }

    /** 初始化tomcat容器 */
    private  void init(Class main){

        tomcat = new Tomcat();
        // HttpServlet对象是你现有的自定义的Servlet容器
        servlet = new DyDispatchedServlet("dy-application.properties",main);

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

    public InsideTomcat() {

    }

    public InsideTomcat(Properties properties) {
        if (properties==null){
            return;
        }
        String p=properties.getProperty("dy.server.port");
        if (!StringUtils.isEmptyPlus(p)){
            this.port=p;
        }
        String c=properties.getProperty("dy.server.code");
        if (!StringUtils.isEmptyPlus(c)){
            this.code=c;
        }
        String h=properties.getProperty("dy.server.hinderURL");
        if (!StringUtils.isEmptyPlus(h)){
            this.hinderURL=h;
        }
        String s=properties.getProperty("dy.server.shiftURL");
        if (!StringUtils.isEmptyPlus(s)){
            this.shiftURL=s;
        }
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHinderURL() {
        return hinderURL;
    }

    public void setHinderURL(String hinderURL) {
        this.hinderURL = hinderURL;
    }

    public String getShiftURL() {
        return shiftURL;
    }

    public void setShiftURL(String shiftURL) {
        this.shiftURL = shiftURL;
    }

    public  static Tomcat getTomcat() {
        return tomcat;
    }


    public HttpServlet getServlet() {
        return servlet;
    }

    public void setServlet(HttpServlet servlet) {
        this.servlet = servlet;
    }
}