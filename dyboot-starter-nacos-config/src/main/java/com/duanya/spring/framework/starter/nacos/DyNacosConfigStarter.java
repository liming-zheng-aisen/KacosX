package com.duanya.spring.framework.starter.nacos;

import com.duanya.spring.framework.annotation.DyBootApplicationStarter;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import com.duanya.spring.framework.starter.DyDefaultStarter;
import com.duanya.spring.nacos.config.DyNacosConfig;
import com.duanya.spring.nacos.config.service.DyNacosConfigService;
import com.duanya.spring.nacos.config.util.DyPropertiesUtil;

import java.util.Properties;

/**
 * @Desc DyNacosConfigStarter
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
@DyBootApplicationStarter(scannerPath = {},order = Integer.MIN_VALUE)
public class DyNacosConfigStarter implements DyDefaultStarter {

    public static Properties defaultEvn=new Properties();

    @Override
    public void doStart(Properties evn, Class cl) throws Exception {

        DyPropertiesUtil.cloneEvn(evn,defaultEvn);

        DyNacosConfig nacosConfig=(DyNacosConfig)DyBeanFactory.initNewBean(DyNacosConfig.class,evn);

        DyNacosConfigService nacosConfigService=DyNacosConfigService.naocsConfigService;

        nacosConfigService.init(nacosConfig);

        Properties newEvn = nacosConfigService.doPullConfig();

        DyPropertiesUtil.cloneEvn(evn,newEvn);

        DyConfigurationLoader.setEvn(newEvn);

        nacosConfigService.doListener();

    }



}
