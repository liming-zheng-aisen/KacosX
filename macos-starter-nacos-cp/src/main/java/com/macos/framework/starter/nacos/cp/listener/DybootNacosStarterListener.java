package com.macos.framework.starter.nacos.cp.listener;

import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.macos.common.ip.IpUtil;
import com.macos.framework.context.ApplicationContextImpl;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.manager.ContextManager;
import com.macos.framework.core.listener.api.LoadListener;
import com.macos.framework.nacos.config.NacosCPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @Desc DybootNacos
 * @Author Zheng.LiMing
 * @Date 2019/9/12
 */
public class DybootNacosStarterListener implements LoadListener {

    private final  static Logger log = LoggerFactory.getLogger(DybootNacosStarterListener.class);
    @Override
    public void notice() {
        try {

            ContextManager manager = ContextManager.BuilderContext.getContextManager();

            NacosCPConfig nacosCPConfig =(NacosCPConfig)manager.getBean("dyNacosCPConfig", NacosCPConfig.class);

            Properties properties = new Properties();
            properties.setProperty("serverAddr", nacosCPConfig.getServerAddr());
            properties.setProperty("namespace", nacosCPConfig.getNamespace());
            NamingService naming = NamingFactory.createNamingService(properties);
            naming.registerInstance(nacosCPConfig.getServiceName(), nacosCPConfig.getGroup(), IpUtil.currentIP("127.0.0.1"), nacosCPConfig.getPort(), nacosCPConfig.getClusterName());
            naming.subscribe("SERVICE_DYBOOT", "DYSPRING_DEFAULT",new EventListener() {
                @Override
                public void onEvent(Event event) {
                    System.out.println(((NamingEvent)event).getServiceName());
                    System.out.println(((NamingEvent)event).getInstances());
                }
            });
            ApplicationContextApi context= ApplicationContextImpl.Builder.getDySpringApplicationContext();
            context.registerBean("namingService",naming);
            log.info("服务 {} 注册成功", nacosCPConfig.getServiceName());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(Properties evn) {

    }
}
