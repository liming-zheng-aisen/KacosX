package com.duanya.spring.framework.starter;

import com.duanya.spring.framework.annotation.DyBootApplicationStarter;
import com.duanya.spring.framework.core.bean.factory.bean.manager.DyBeanManager;
import com.duanya.spring.framework.core.listener.api.manager.DyLoaderListerManager;
import com.duanya.start.web.jetty.DyJettyServerStarterListener;
import com.duanya.start.web.jetty.filter.init.DyFilterRegisterServer;
import com.duanya.start.web.jetty.servlet.DyServletBeanInitManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        Set<Class> classSet=DyBeanManager.getClassContainer();

        DyFilterRegisterServer filterRegisterServer=new DyFilterRegisterServer();
        filterRegisterServer.autoRegisterFilter(classSet);

        DyServletBeanInitManager servletBeanInitManager=DyServletBeanInitManager.Builder.getDyServletBeanInitManager();
        servletBeanInitManager.init(classSet);

        DyJettyServerStarterListener serverStarterListener= new DyJettyServerStarterListener();
        log.info("注册TomcatStarterListener监听器");
        DyLoaderListerManager.registerLister(serverStarterListener);

    }

}
