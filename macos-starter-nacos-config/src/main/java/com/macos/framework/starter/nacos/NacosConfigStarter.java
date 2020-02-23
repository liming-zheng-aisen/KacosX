package com.macos.framework.starter.nacos;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.macos.framework.annotation.MacosXApplicationStarter;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.starter.DefaultStarter;
import com.macos.nacos.config.NacosConfig;
import com.macos.nacos.config.service.NacosConfigService;
import com.macos.nacos.config.util.PropertiesUtil;

import java.util.Properties;

/**
 * @Desc DyNacosConfigStarter
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
@MacosXApplicationStarter(scannerPath = {},order = Integer.MIN_VALUE)
public class NacosConfigStarter implements DefaultStarter {

    public static Properties defaultEvn=new Properties();

    @Override
    public void doStart(ApplicationENV env, Class main , String[] args) throws Exception {

//        PropertiesUtil.cloneEvn(evn,defaultEvn);
//
//        NacosConfig nacosConfig=(NacosConfig) BeanUtil.initNewBean(NacosConfig.class,evn);
//
//        NacosConfigService nacosConfigService =  NacosConfigService.getNaocsConfigService();
//
//        nacosConfigService.init(nacosConfig);
//
//        Properties newEvn = nacosConfigService.doPullConfig();
//
//        PropertiesUtil.cloneEvn(evn,newEvn);
//
//        PropertiesFileLoader.setEvn(newEvn);
//
//        nacosConfigService.doListener();

    }



}
