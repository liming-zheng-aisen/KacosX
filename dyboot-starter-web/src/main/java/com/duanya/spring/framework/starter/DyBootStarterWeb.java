package com.duanya.spring.framework.starter;

import com.duanya.spring.framework.annotation.DyBootApplicationStarter;
import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
import com.duanya.spring.framework.core.listener.api.manager.DyLoaderListerManager;
import com.duanya.spring.framework.mvc.dispatcher.DyDispatchedServlet;
import com.duanya.start.web.tomcat.TomcatStarterListener;

import java.util.Properties;

/**
 * @Desc DyBootStarterWeb
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
@DyBootApplicationStarter(scannerPath = {"com.duanya.start.web.tomcat"})
public class DyBootStarterWeb implements DyDefaultStarter {

    @Override
    public void doStart(Properties evn, Class cl, DySpringApplicationContext context) throws Exception {
        DyLoaderListerManager.registerLister(new TomcatStarterListener());
        context.registeredBean("dyDispatchedServlet",new DyDispatchedServlet(null,cl,evn));
    }

}
