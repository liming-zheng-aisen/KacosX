package com.mx.framework.starter.nacos.cp.listener;

import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.mx.common.ip.IpUtil;
import com.mx.framework.context.base.ApplicationContextApi;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.context.ApplicationContextImpl;
import com.mx.framework.core.env.ApplicationENV;
import com.mx.framework.core.listener.api.MacosXListener;
import com.mx.framework.nacos.config.NacosCPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @Desc DybootNacos
 * @Author Zheng.LiMing
 * @Date 2019/9/12
 */
public class DybootNacosStarterListener implements MacosXListener {

    private final  static Logger log = LoggerFactory.getLogger(DybootNacosStarterListener.class);
    @Override
    public void notice() {
        try {

            BeanManager manager = new BeanManager();

            NacosCPConfig nacosCPConfig =(NacosCPConfig)manager.getBean("nacosCPConfig", NacosCPConfig.class);

            Properties properties = new Properties();
            properties.setProperty("serverAddr", nacosCPConfig.getServerAddr());
            properties.setProperty("namespace", nacosCPConfig.getNamespace());
            NamingService naming = NamingFactory.createNamingService(properties);
            naming.registerInstance(nacosCPConfig.getServiceName(), nacosCPConfig.getGroup(), IpUtil.currentIP("0.0.0.0"), nacosCPConfig.getPort(), nacosCPConfig.getClusterName());
            naming.subscribe(nacosCPConfig.getServiceName(), nacosCPConfig.getGroup(),new EventListener() {
                @Override
                public void onEvent(Event event) {
                    System.out.println(((NamingEvent)event).getServiceName());
                    System.out.println(((NamingEvent)event).getInstances());
                }
            });
            ApplicationContextApi context= ApplicationContextImpl.Builder.getApplicationContext();
            context.registerBean("namingService",naming);
            log.info("服务 {} 注册成功", nacosCPConfig.getServiceName());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void update(ApplicationENV evn) {

    }
}
