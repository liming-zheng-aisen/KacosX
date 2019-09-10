package com.duanya.spring.framework.starter;

import com.duanya.spring.framework.annotation.DyBootApplicationStarter;
import com.duanya.spring.framework.core.listener.api.manager.DyLoaderListerManager;
import com.duanya.start.web.jetty.JettyServerStarterListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @Desc DyBootStarterWeb
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
@DyBootApplicationStarter(scannerPath = {"com.duanya.start.web.jetty"})
public class DyBootStarterWeb implements DyDefaultStarter {

    private final static Logger log=LoggerFactory.getLogger(DyBootStarterWeb.class);

    @Override
    public void doStart(Properties evn, Class cl) throws Exception {

        JettyServerStarterListener serverStarterListener= new JettyServerStarterListener();
        serverStarterListener.setCl(cl);
        serverStarterListener.setProperties(evn);

        log.info("注册TomcatStarterListener监听器");
        DyLoaderListerManager.registerLister(serverStarterListener);


    }

}
