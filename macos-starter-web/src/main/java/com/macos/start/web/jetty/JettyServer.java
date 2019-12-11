package com.macos.start.web.jetty;

import com.duanya.spring.framework.mvc.util.JsonUtil;
import com.macos.common.times.Timer;
import com.macos.framework.context.manager.ContextManager;
import com.macos.framework.core.bean.factory.BeanFactory;
import com.macos.framework.core.load.conf.ConfigurationLoader;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc DyJettyServer
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class JettyServer {


    private final static Logger log=LoggerFactory.getLogger(JettyServer.class);

    private Server server;

    private JettyConfig config;

    public   void start() throws Exception {

        JettyConfig config=(JettyConfig) BeanFactory.initNewBean(JettyConfig.class, ConfigurationLoader.getEvn());

        copyConfig(config);

        server = JettyFactory.createServer(config,this.getClass());


        server.start();

        ContextManager context=ContextManager.BuilderContext.getContextManager();
        Timer dyTimer=(Timer) context.getBean("dyTimer",Timer.class);
        log.info("DyBoot Starter Web 启动完成，花费时间为{}ms",dyTimer.spendingTime());

        //server.join();

    }


    public void destroy(){
        server.destroy();
    }

    private void copyConfig(JettyConfig config){
        try {
            String data = JsonUtil.objectToJson(config);
            this.config=JsonUtil.jsonToBean(data, JettyConfig.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public JettyConfig getConfig() {
        return config;
    }

    public void setConfig(JettyConfig config) {
        this.config = config;
    }
}
