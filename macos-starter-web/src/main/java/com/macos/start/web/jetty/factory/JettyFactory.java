package com.macos.start.web.jetty.factory;

import com.macos.common.ip.IpUtil;
import com.macos.framework.mvc.dispatcher.DispatchedServlet;
import com.macos.start.web.jetty.conf.JettyConfig;
import com.macos.start.web.jetty.filter.FilterBean;
import com.macos.start.web.jetty.filter.WebFilterMannager;
import com.macos.start.web.jetty.handlle.StaticSourceHandle;
import com.macos.start.web.jetty.servlet.ServletBean;
import com.macos.start.web.jetty.servlet.ServletBeanInitManager;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;

import javax.servlet.DispatcherType;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Set;

/**
 * @Desc Jetty
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class JettyFactory {


    private final static  String THREAD_NAME="DYBOOT";


    public static Server createServer(JettyConfig config, Class main) throws Exception {

        //初始化连接池
        ThreadPool threadPool=initThreadPool(config);
        //创建一个jetty服务
        Server server=new Server(threadPool);
        //获取ip
        String host=config.getHost();
        //创建连接绑定
        ServerConnector http=initConnectory(server,host,config.getPort(),config.getIdleTimeout());
        //添加一个连接
        server.addConnector(http);


        //如果是本地的话，新增一个连接，作为外网通讯
        if (host.equals("localhost")){
            ServerConnector http2 = initConnectory(server, IpUtil.currentIP(host),config.getPort(),config.getIdleTimeout());
            server.addConnector(http2);
        }

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/*");
        context.setResourceBase(System.getProperty("java.io.tmpdir"));
        context.setServer(server);

        ServletHandler servletHandler=new ServletHandler();

        setFilterHandler(servletHandler);
        setServletHandler(servletHandler);
        setDyDispatchedServlet(servletHandler,config);

        context.setServletHandler(servletHandler);


        StaticSourceHandle staticSourceHandle=initStaticSourceHandle(config,main);

        HandlerList handlerList=new HandlerList();

        handlerList.setHandlers(new Handler[]{staticSourceHandle,context});

        server.setHandler(handlerList);

        return server;
    }

    private static ThreadPool initThreadPool(JettyConfig config){
        QueuedThreadPool threadPool=new QueuedThreadPool();
        threadPool.setMinThreads(config.getMinThreads());
        threadPool.setMaxThreads(config.getMaxThreads());
        threadPool.setName(THREAD_NAME);
        return threadPool;
    }

    private static ServerConnector initConnectory(Server server,String host,int port,int timeout){
        ServerConnector http = new ServerConnector(server);
        http.setHost(host);
        http.setPort(port);
        http.setIdleTimeout(timeout);
        return http;
    }

    private static void setFilterHandler(ServletHandler servletHandler){
        if (null==servletHandler){
            return;
        }

        Set<FilterBean> filterBeans= WebFilterMannager.getFilterBeanSet();
        if (filterBeans==null||filterBeans.size()==0){
            return;
        }
        for (FilterBean filterBean:filterBeans){
            FilterHolder filterHolder=new FilterHolder();
            filterHolder.setFilter(filterBean.getFilter());
            filterHolder.setName(filterBean.getFilterName());
            servletHandler.addFilterWithMapping(filterHolder,filterBean.getUrl(),EnumSet.of(DispatcherType.REQUEST));
        }
    }


    private static void setServletHandler(ServletHandler servletHandler){
        Set<ServletBean> servletBeans= ServletBeanInitManager.Builder.getServletBeanInitManager().getServletBeans();
        if(servletBeans==null||servletBeans.size()==0){
            return;
        }
        for (ServletBean servletBean:servletBeans){
            ServletHolder servletHolder=new ServletHolder();
            servletHolder.setServlet(servletBean.getServlet());
            servletHandler.addServletWithMapping(servletHolder,servletBean.getUrl());
        }
    }

    private static StaticSourceHandle initStaticSourceHandle(JettyConfig config, Class main){
        StaticSourceHandle staticSourceHandle=new StaticSourceHandle();
        staticSourceHandle.setSuffixRegex(config.getSufix());
        staticSourceHandle.setBaseResource(Resource.newResource(main.getClassLoader().getResource(config.getSourcePath())));
        staticSourceHandle.setDirectoriesListed(true);
        staticSourceHandle.setIgnorePath(config.getIgnorePath());
        return staticSourceHandle;
    }

    private static void setDyDispatchedServlet(ServletHandler servletHandler, JettyConfig config){
        DispatchedServlet dispatchedServlet =new DispatchedServlet();
        ServletHolder servletHolder=new ServletHolder();
        servletHolder.setServlet(dispatchedServlet);
        servletHandler.addServletWithMapping(servletHolder,config.getDefaultPaht());
    }

    static class DyServletConfig implements ServletConfig{

        @Override
        public String getServletName() {
            return null;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public String getInitParameter(String s) {
            return null;
        }

        @Override
        public Enumeration<String> getInitParameterNames() {
            return null;
        }
    }
}
