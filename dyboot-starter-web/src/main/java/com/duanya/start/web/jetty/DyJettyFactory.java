package com.duanya.start.web.jetty;

import com.duanya.spring.framework.mvc.dispatcher.DyDispatchedServlet;
import com.duanya.start.web.jetty.filter.DyFilterBean;
import com.duanya.start.web.jetty.filter.DyWebFilterMannager;
import com.duanya.start.web.jetty.handlle.DyStaticSourceHandle;
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

import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.Set;

/**
 * @Desc Jetty
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class DyJettyFactory {
    public static Server createServer(DyJettyConfig config,Class main){

        Server server=new Server();
        server.setStopAtShutdown(true);
        ServerConnector http = new ServerConnector(server);
        http.setHost(config.getHost());
        http.setPort(config.getPort());
        http.setIdleTimeout(config.getIdleTimeout());
        server.addConnector(http);

        ServletHandler servletHandler=new ServletHandler();
        setFilterHandler(servletHandler);
        setServletHandler(servletHandler);
        setDyDispatchedServlet(servletHandler,config);

        DyStaticSourceHandle staticSourceHandle=initStaticSourceHandle(config,main);

        HandlerList handlerList=new HandlerList();
        handlerList.setHandlers(new Handler[]{staticSourceHandle,servletHandler});

        server.setHandler(handlerList);

        return server;
    }

    private static  ServletHandler setFilterHandler(ServletHandler servletHandler){
        if (null==servletHandler){
            return null;
        }

        Set<DyFilterBean> filterBeans= DyWebFilterMannager.getFilterBeanSet();
        if (filterBeans==null||filterBeans.size()==0){
            return servletHandler;
        }
        for (DyFilterBean filterBean:filterBeans){
            FilterHolder filterHolder=new FilterHolder();
            filterHolder.setFilter(filterBean.getFilter());
            filterHolder.setName(filterBean.getFilterName());
            servletHandler.addFilterWithMapping(filterHolder,filterBean.getUrl(),EnumSet.of(DispatcherType.REQUEST));
        }
        return servletHandler;
    }


    private static void setServletHandler(ServletHandler servletHandler){
        Set<DyServletBean> servletBeans=DyServletBeanInitManager.Builder.getDyServletBeanInitManager().getServletBeans();
        if(servletBeans==null||servletBeans.size()==0){
            return;
        }
        for (DyServletBean servletBean:servletBeans){
            ServletHolder servletHolder=new ServletHolder();
            servletHolder.setServlet(servletBean.getServlet());
            servletHandler.addServletWithMapping(servletHolder,servletBean.getUrl());
        }
    }

    private static DyStaticSourceHandle initStaticSourceHandle(DyJettyConfig config,Class main){
        DyStaticSourceHandle staticSourceHandle=new DyStaticSourceHandle();
        staticSourceHandle.setPrefixRegex(config.getPrefix());
        staticSourceHandle.setSuffixRegex(config.getSufix());
        staticSourceHandle.setBaseResource(Resource.newResource(main.getClassLoader().getResource(config.getSourcePath())));
        staticSourceHandle.setDirectoriesListed(true);
        return staticSourceHandle;
    }

    private static void setDyDispatchedServlet(ServletHandler servletHandler, DyJettyConfig config){
        DyDispatchedServlet dyDispatchedServlet=new DyDispatchedServlet();
        ServletHolder servletHolder=new ServletHolder();
        servletHolder.setServlet(dyDispatchedServlet);
        servletHandler.addServletWithMapping(servletHolder,config.getDefaultPaht());
    }
}
