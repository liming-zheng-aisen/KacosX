package com.mx.framework.starter.web.server;

import com.mx.common.times.Timer;
import com.mx.console.log.Logger;
import com.mx.console.log.factory.LoggerFactory;
import com.mx.framework.context.base.ApplicationContextApi;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.context.ApplicationContextImpl;
import com.mx.framework.starter.web.factory.JettyFactory;
import com.mx.framework.starter.web.conf.JettyConfig;
import org.eclipse.jetty.server.Server;



/**
 * @Desc
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class JettyServer {


    private final static Logger log= LoggerFactory.buildLogger(JettyServer.class);

    ApplicationContextApi contextApi = ApplicationContextImpl.Builder.getApplicationContext();

    private Server server;

    public void start() throws Exception {

        JettyConfig config=(JettyConfig) BeanManager.getBeanDefinition(null,JettyConfig.class,true).getBean(null,JettyConfig.class);

        server = JettyFactory.createServer(config,this.getClass());

        server.start();

        Timer macosXTimer=(Timer)contextApi.getBean("MXTimer",Timer.class);
        log.success("MX Application启动完成，花费时间为{1}ms",macosXTimer.spendingTime());
        server.join();


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
