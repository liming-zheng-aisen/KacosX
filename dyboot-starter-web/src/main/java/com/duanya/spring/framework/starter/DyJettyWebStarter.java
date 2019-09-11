package com.duanya.spring.framework.starter;

import com.duanya.spring.common.scanner.api.IDyScanner;
import com.duanya.spring.common.scanner.impl.DyScannerImpl;
import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.*;
import com.duanya.spring.framework.core.listener.api.manager.DyLoaderListerManager;
import com.duanya.start.web.jetty.DyJettyServerStarterListener;
import com.duanya.start.web.jetty.filter.init.DyFilterRegisterServer;
import com.duanya.start.web.jetty.servlet.DyServletBeanInitManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @Desc DyBootStarterWeb
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
@DyBootApplicationStarter(scannerPath = {},order = Integer.MAX_VALUE)
public class DyJettyWebStarter implements DyDefaultStarter {

    private final static Logger log=LoggerFactory.getLogger(DyJettyWebStarter.class);

    @Override
    public void doStart(Properties evn, Class cl) throws Exception {

        Set<Class> classSet=null;

        Class[] classes=new Class[]{DyRestController.class,DyWebServlet.class,DyWebFilter.class};

        IDyScanner scanner=new DyScannerImpl();
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



        DyJettyServerStarterListener serverStarterListener= new DyJettyServerStarterListener();
        log.info("注册TomcatStarterListener监听器");
        DyLoaderListerManager.registerLister(serverStarterListener);


    }

}
