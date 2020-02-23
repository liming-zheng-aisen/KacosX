package com.macos.framework.starter;

import com.macos.framework.annotation.MacosXApplicationStarter;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.core.handle.base.BaseHandler;
import com.macos.framework.core.listener.manager.MacosXListerManager;
import com.macos.framework.mvc.handler.mvc.*;
import com.macos.framework.starter.combiner.RestApiCombiner;
import com.macos.framework.starter.web.listener.JettyServerStarterListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashSet;
import java.util.Set;

/**
 * @Desc DyBootStarterWeb
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
@MacosXApplicationStarter(scannerPath = {"com.macos.framework.starter"},order =Integer.MAX_VALUE)
public class JettyWebStarter implements DefaultStarter {

    private final static Logger log= LoggerFactory.getLogger(JettyWebStarter.class);

    public Set<BaseHandler> mvcHandler = new HashSet<>();

    @Override
    public void doStart(ApplicationENV env, Class main, String[] args) throws Exception {


        JettyServerStarterListener serverStarterListener= new JettyServerStarterListener();
        log.debug("注册TomcatStarterListener监听器");
        MacosXListerManager.registerLister(serverStarterListener);
    }


}
