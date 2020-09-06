package com.mx.framework.starter.web.listener;

import com.mx.core.combiner.ValueCombiner;
import com.mx.framework.annotation.http.RestAPI;
import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.env.ApplicationENV;
import com.mx.framework.core.handle.base.BaseHandler;
import com.mx.framework.core.listener.api.MacosXListener;
import com.mx.framework.mvc.handler.mvc.RestApiHandler;
import com.mx.framework.starter.combiner.RestApiCombiner;
import com.mx.framework.starter.web.conf.JettyConfig;
import com.mx.framework.starter.web.server.JettyServer;

import java.util.Set;

/**
 * @Desc TomcatStarterListener
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class JettyServerStarterListener implements MacosXListener {

    private JettyServer server;


    private JettyServer serverUpdate;

    @Override
    public void notice() {
        try {
            startMVC();
            server= new JettyServer();
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void startMVC() throws Exception {
        Set<BeanDefinition> classSet = BeanManager.getBeanDefinitionsByAnnotation(RestAPI.class);
        RestApiHandler restApiHandler = (RestApiHandler) RestApiCombiner.getCombiner().getHandler();
        for (BeanDefinition beanDefinition : classSet){
            restApiHandler.doHandle(null,beanDefinition.getTarget(),null);
        }
    }


    @Override
    public void update(ApplicationENV evn) {
        try {

            BeanManager beanManager = new BeanManager();
            JettyConfig config = (JettyConfig) beanManager.getBean(null,JettyConfig.class);

            String configStrOld = config.toString();

            BaseHandler handler =  ValueCombiner.getCombiner().getHandler();

            handler.doHandle(null,JettyConfig.class,null);

            String configStrNew = config.toString();


            if (configStrNew.equals(configStrOld)){
                serverUpdate=new JettyServer();
                serverUpdate.start();
                server.stop();
                server.destroy();
                server=null;
                server=serverUpdate;
            }

            Set<BeanDefinition> beanDefinitions = BeanManager.getClassContainer();

            beanDefinitions.stream().forEach(bean-> {
                try {
                    handler.doHandle(null,bean.getTarget(),null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}
