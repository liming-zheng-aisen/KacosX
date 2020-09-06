package com.mx.framework.starter;

import com.mx.console.log.Logger;
import com.mx.console.log.factory.LoggerFactory;
import com.mx.framework.annotation.boot.MacosXApplicationStarter;
import com.mx.framework.core.env.ApplicationENV;
import com.mx.framework.core.handle.base.BaseHandler;
import com.mx.framework.core.listener.manager.MacosXListerManager;
import com.mx.framework.starter.web.listener.JettyServerStarterListener;
import com.mx.framework.starter.web.log.JettyLogger;
import org.eclipse.jetty.util.log.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * @Desc
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
@MacosXApplicationStarter(scannerPath = {"com.mx.framework.starter"},order =Integer.MAX_VALUE)
public class JettyWebStarter implements DefaultStarter {

    private final static Logger log= LoggerFactory.buildLogger(JettyWebStarter.class);

    public Set<BaseHandler> mvcHandler = new HashSet<>();

    @Override
    public void doStart(ApplicationENV env, Class main, String[] args) throws Exception {
        JettyServerStarterListener serverStarterListener= new JettyServerStarterListener();
        log.success("注册TomcatStarterListener监听器");
        MacosXListerManager.registerLister(serverStarterListener);
        JettyLogger jettyLogger=new JettyLogger();
        jettyLogger.setDebugEnabled(false);
        Log.setLog(jettyLogger);

    }


}
