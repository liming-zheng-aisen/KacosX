package com.duanya.start.web.jetty;

import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
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

    private DyJettyServer jettyServer;


    @Override
    public void notice() {
        try {
            jettyServer=new DyJettyServer();
            jettyServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Properties evn) {
        try {
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
