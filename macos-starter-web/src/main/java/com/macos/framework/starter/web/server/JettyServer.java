package com.macos.framework.starter.web.server;

import com.macos.common.times.Timer;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.context.ApplicationContextImpl;
import com.macos.framework.starter.web.factory.JettyFactory;
import com.macos.framework.starter.web.conf.JettyConfig;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class JettyServer {


    private final static Logger log=LoggerFactory.getLogger(JettyServer.class);

    ApplicationContextApi contextApi = ApplicationContextImpl.Builder.getApplicationContext();

    private Server server;

    public void start() throws Exception {

        JettyConfig config=(JettyConfig) BeanManager.getBeanDefinition(null,JettyConfig.class,true).getBean(null,JettyConfig.class);

        server = JettyFactory.createServer(config,this.getClass());

        server.start();

        Timer macosXTimer=(Timer)contextApi.getBean("MacosXTimer",Timer.class);

        log.info("MacosX Application启动完成，花费时间为{}ms",macosXTimer.spendingTime());

        //server.join();

    }


    public void destroy(){
        server.destroy();
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

}
