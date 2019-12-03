package com.macos.framework.starter.nacos;

import com.macos.framework.annotation.MacosApplicationStarter;
import com.macos.framework.core.bean.factory.BeanFactory;
import com.macos.framework.core.load.ConfigurationLoader;
import com.macos.nacos.config.NacosConfig;
import com.macos.nacos.config.service.NacosConfigService;
import com.macos.nacos.config.util.PropertiesUtil;

import java.util.Properties;

/**
 * @Desc DyNacosConfigStarter
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
@MacosApplicationStarter(scannerPath = {},order = Integer.MIN_VALUE)
public class NacosConfigStarter implements com.duanya.spring.framework.starter.DefaultStarter {

    public static Properties defaultEvn=new Properties();

    @Override
    public void doStart(Properties evn, Class cl) throws Exception {

        PropertiesUtil.cloneEvn(evn,defaultEvn);

        NacosConfig nacosConfig=(NacosConfig) BeanFactory.initNewBean(NacosConfig.class,evn);

        NacosConfigService nacosConfigService =  NacosConfigService.getNaocsConfigService();

        nacosConfigService.init(nacosConfig);

        Properties newEvn = nacosConfigService.doPullConfig();

        PropertiesUtil.cloneEvn(evn,newEvn);

        ConfigurationLoader.setEvn(newEvn);

        nacosConfigService.doListener();

    }



}
