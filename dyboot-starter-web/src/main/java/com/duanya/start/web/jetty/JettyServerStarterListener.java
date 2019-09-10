package com.duanya.start.web.jetty;

import com.duanya.spring.common.scanner.api.IDyScanner;
import com.duanya.spring.common.scanner.impl.DyScannerImpl;
import com.duanya.spring.common.times.DyTimer;
import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.*;
import com.duanya.spring.framework.context.manager.DyContextManager;
import com.duanya.spring.framework.core.listener.api.IDyLoadListener;
import com.duanya.spring.framework.mvc.dispatcher.DyDispatchedServlet;
import com.duanya.start.web.jetty.filter.DyFilterBean;
import com.duanya.start.web.jetty.filter.DyWebFilterMannager;
import com.duanya.start.web.jetty.filter.init.DyFilterRegisterServer;
import com.duanya.start.web.jetty.servlet.DyServletBean;
import com.duanya.start.web.jetty.servlet.DyServletBeanInitManager;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


/**
 * @Desc TomcatStarterListener
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class JettyServerStarterListener implements IDyLoadListener {

   private final static Logger log=LoggerFactory.getLogger(JettyServerStarterListener.class);

   private Properties properties;

   private Class cl;

    @Override
    public void notice() {
        try {

            DyContextManager context=DyContextManager.BuilderContext.getContextManager();

            DyTimer dyTimer=(DyTimer) context.getBean("dyTimer",DyTimer.class);

            JettyConfig jettyConfig= (JettyConfig) context.getBean("jettyConfig",JettyConfig.class);

            IDyScanner scanner=new DyScannerImpl();

            Set<Class> classSet=null;

            Class[] classes=new Class[]{DyRestController.class,DyWebServlet.class,DyWebFilter.class};

            if (cl.isAnnotationPresent(DyScanner.class)){
                DyScanner dyScanner=(DyScanner) cl.getAnnotation(DyScanner.class);
                String[] scanPaths=dyScanner.packageNames();
                classSet=new HashSet<>();
                for (String path:scanPaths){
                    classSet.addAll(scanner.doScanner(StringUtils.trim(path),classes));
                }
            }else if (cl.isAnnotationPresent(DyBootApplication.class)) {
                classSet=scanner.doScanner(cl.getPackage().getName(),classes);
            }

            DyFilterRegisterServer filterRegisterServer=new DyFilterRegisterServer();
            filterRegisterServer.autoRegisterFilter(classSet);

            DyServletBeanInitManager servletBeanInitManager=DyServletBeanInitManager.Builder.getDyServletBeanInitManager();
            servletBeanInitManager.init(classSet);


            ServletHandler servletHandler=new ServletHandler();
            setFilterHandler(servletHandler);
            setServletHandler(servletHandler);
            setDyDispatchedServlet(servletHandler,jettyConfig,classSet);

            StaticSourceHandle staticSourceHandle=initStaticSourceHandle(jettyConfig);

            HandlerList handlerList=new HandlerList();
            handlerList.setHandlers(new Handler[]{staticSourceHandle,servletHandler});

            Server server = initJetty(jettyConfig,handlerList);

            server.start();

            log.info("DyBoot Starter Web 启动完成，花费时间为{}ms",dyTimer.spendingTime());

            server.join();


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private Server initJetty(JettyConfig config, HandlerList handlerList){
        Server server=new Server();
        server.setStopAtShutdown(true);
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(config.getPort());
        http.setIdleTimeout(120000);
        server.addConnector(http);
        server.setHandler(handlerList);
        return server;
    }

    private ServletHandler setFilterHandler(ServletHandler servletHandler){
        if (null==servletHandler){
            return null;
        }

        Set<DyFilterBean> filterBeans= DyWebFilterMannager.getFilterBeanSet();
        for (DyFilterBean filterBean:filterBeans){
            FilterHolder filterHolder=new FilterHolder();
            filterHolder.setFilter(filterBean.getFilter());
            filterHolder.setName(filterBean.getFilterName());
            servletHandler.addFilterWithMapping(filterHolder,filterBean.getUrl(),EnumSet.of(DispatcherType.REQUEST));
        }
        return servletHandler;
    }

    private void setServletHandler(ServletHandler servletHandler){
        Set<DyServletBean> servletBeans=DyServletBeanInitManager.Builder.getDyServletBeanInitManager().getServletBeans();
        for (DyServletBean servletBean:servletBeans){
            ServletHolder servletHolder=new ServletHolder();
            servletHolder.setServlet(servletBean.getServlet());
            servletHandler.addServletWithMapping(servletHolder,servletBean.getUrl());
        }
    }

    private StaticSourceHandle initStaticSourceHandle(JettyConfig config){
        StaticSourceHandle staticSourceHandle=new StaticSourceHandle();
        staticSourceHandle.setPrefixRegex(config.getPrefix());
        staticSourceHandle.setSuffixRegex(config.getSufix());
        staticSourceHandle.setBaseResource(Resource.newResource(cl.getClassLoader().getResource(config.getSourcePath())));
        staticSourceHandle.setDirectoriesListed(true);
        return staticSourceHandle;
    }

    private void setDyDispatchedServlet(ServletHandler servletHandler,JettyConfig config,Set<Class> classes){
            DyDispatchedServlet dyDispatchedServlet=new DyDispatchedServlet(properties,classes);
            ServletHolder servletHolder=new ServletHolder();
            servletHolder.setServlet(dyDispatchedServlet);
            servletHandler.addServletWithMapping(servletHolder,config.getDefaultPaht());
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Class getCl() {
        return cl;
    }

    public void setCl(Class cl) {
        this.cl = cl;
    }
}
