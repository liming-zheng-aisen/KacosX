package com.mx.nacos.config.listener;

import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.env.ApplicationENV;
import com.mx.framework.core.listener.api.MacosXListener;
import com.mx.nacos.config.NacosConfig;
import com.mx.nacos.config.service.NacosConfigService;

import java.util.Properties;

/**
 * @Desc NacosStarterListener
 * @Author Zheng.LiMing
 * @Date 2020/2/24
 */
public class NacosStarterListener implements MacosXListener {
    @Override
    public void notice() {
        BeanManager beanManager = new BeanManager();
        NacosConfig nacosConfig= null;
        try {
            ApplicationENV env = (ApplicationENV) beanManager.getBean(null,ApplicationENV.class);
            nacosConfig = (NacosConfig) beanManager.getBean(null,NacosConfig.class);
            NacosConfigService nacosConfigService =  NacosConfigService.getNaocsConfigService();
            nacosConfigService.init(nacosConfig);
            Properties properties = nacosConfigService.doPullConfig();
            env.addElementsByPropreties(properties);
            nacosConfigService.doListener();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(ApplicationENV evn) {

    }
}
