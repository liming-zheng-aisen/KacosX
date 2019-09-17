package com.duanya.spring.framework.starter.nacos.cp.listener;

import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.duanya.spring.common.ip.IpUtil;
import com.duanya.spring.framework.context.manager.DyContextManager;
import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
import com.duanya.spring.framework.core.listener.api.IDyLoadListener;
import com.duanya.spring.framework.nacos.config.DyNacosCPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @Desc DybootNacos
 * @Author Zheng.LiMing
 * @Date 2019/9/12
 */
public class DybootNacosStarterListener implements IDyLoadListener {

    private final  static Logger log = LoggerFactory.getLogger(DybootNacosStarterListener.class);
    @Override
    public void notice() {
        try {

            DyContextManager manager=DyContextManager.BuilderContext.getContextManager();

            DyNacosCPConfig dyNacosCPConfig=(DyNacosCPConfig)manager.getBean("dyNacosCPConfig",DyNacosCPConfig.class);

            Properties properties = new Properties();
            properties.setProperty("serverAddr",dyNacosCPConfig.getServerAddr());
            properties.setProperty("namespace", dyNacosCPConfig.getNamespace());
            NamingService naming = NamingFactory.createNamingService(properties);
            naming.registerInstance(dyNacosCPConfig.getServiceName(), dyNacosCPConfig.getGroup(),IpUtil.currentIP("127.0.0.1"), dyNacosCPConfig.getPort(), dyNacosCPConfig.getClusterName());
            naming.subscribe("SERVICE_DYBOOT", "DYSPRING_DEFAULT",new EventListener() {
                @Override
                public void onEvent(Event event) {
                    System.out.println(((NamingEvent)event).getServiceName());
                    System.out.println(((NamingEvent)event).getInstances());
                }
            });
            DySpringApplicationContext context=DySpringApplicationContext.Builder.getDySpringApplicationContext();
            context.registerBean("namingService",naming);
            log.info("服务 {} 注册成功",dyNacosCPConfig.getServiceName());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(Properties evn) {

    }
}
