package com.duanya.start.web.jetty;

import com.duanya.spring.common.times.DyTimer;
import com.duanya.spring.framework.context.manager.DyContextManager;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc DyJettyServer
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class DyJettyServer {


    private final static Logger log=LoggerFactory.getLogger(DyJettyServer.class);

    private Server server;

    private DyJettyConfig config;

    public   void start() throws Exception {

        config=(DyJettyConfig) DyBeanFactory.initNewBean(DyJettyConfig.class,DyConfigurationLoader.getEvn());

        server =DyJettyFactory.createServer(config,this.getClass());

        server.start();

        DyContextManager context=DyContextManager.BuilderContext.getContextManager();
        DyTimer dyTimer=(DyTimer) context.getBean("dyTimer",DyTimer.class);
        log.info("DyBoot Starter Web 启动完成，花费时间为{}ms",dyTimer.spendingTime());

        server.join();

    }


    public void stop() throws Exception {
        server.stop();
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public DyJettyConfig getConfig() {
        return config;
    }

    public void setConfig(DyJettyConfig config) {
        this.config = config;
    }
}
