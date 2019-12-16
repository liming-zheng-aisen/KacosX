package com.macos.start.web.jetty.listener;

import com.macos.framework.context.ApplicationContextImpl;
import com.macos.framework.core.bean.factory.BeanFactory;
import com.macos.framework.core.bean.factory.ValueFactory;
import com.macos.framework.core.listener.api.LoadListener;
import com.macos.start.web.jetty.conf.JettyConfig;
import com.macos.start.web.jetty.server.JettyServer;

import java.util.Map;
import java.util.Properties;


/**
 * @Desc TomcatStarterListener
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class JettyServerStarterListener implements LoadListener {

    private JettyServer server;


    private JettyServer serverUpdate;

    @Override
    public void notice() {
        try {
            server= new JettyServer();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void update(Properties evn) {
        try {
            JettyConfig config2= (JettyConfig)  BeanFactory.initNewBean(JettyConfig.class,evn);
            if (!config2.toString().equals(server.getConfig().toString())){
                serverUpdate=new JettyServer();
                serverUpdate.start();
                server.stop();
                server.destroy();
                server=null;
                server=serverUpdate;
            }
            ApplicationContextImpl context= ApplicationContextImpl.Builder.getDySpringApplicationContext();
            Map<String,Object> objectMap= context.getContext();
            objectMap.values().stream().forEach(bean-> {
                try {
                    ValueFactory.doFields(bean,evn);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}
