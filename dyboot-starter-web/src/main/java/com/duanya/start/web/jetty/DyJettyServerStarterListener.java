package com.duanya.start.web.jetty;

import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.bean.factory.DyValueFactory;
import com.duanya.spring.framework.core.listener.api.IDyLoadListener;

import java.util.Map;
import java.util.Properties;


/**
 * @Desc TomcatStarterListener
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyJettyServerStarterListener implements IDyLoadListener {

    private DyJettyServer server;


    private DyJettyServer serverUpdate;

    @Override
    public void notice() {
        try {
            server= new DyJettyServer();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void update(Properties evn) {
        try {
            DyJettyConfig config2=(DyJettyConfig)DyBeanFactory.initNewBean(DyJettyConfig.class,evn);
            if (!config2.toString().equals(server.getConfig().toString())){
                serverUpdate=new DyJettyServer();
                serverUpdate.start();
                server.stop();
                server.destroy();
                server=null;
                server=serverUpdate;
            }
            DySpringApplicationContext context=DySpringApplicationContext.Builder.getDySpringApplicationContext();
            Map<String,Object> objectMap= context.getContext();
            objectMap.values().stream().forEach(bean-> {
                try {
                    DyValueFactory.doFields(bean,evn);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}
