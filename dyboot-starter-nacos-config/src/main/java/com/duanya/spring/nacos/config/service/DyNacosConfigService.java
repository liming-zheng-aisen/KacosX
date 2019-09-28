package com.duanya.spring.nacos.config.service;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.duanya.spring.nacos.config.DyNacosConfig;
import com.duanya.spring.nacos.config.DyNacosPropertisMannager;
import com.duanya.spring.nacos.config.listener.DyNacosListener;

import java.io.IOException;
import java.util.Properties;

/**
 * @Desc DyNacosConfigService
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */

public class DyNacosConfigService {

    private  ConfigService configService;

    private  String serverAddr;

    private  String[] dataIds;

    private  String group;

    private  Integer timeoutMs;

    private static DyNacosConfigService naocsConfigService=new DyNacosConfigService();

    private DyNacosConfigService(){

    }


    public void init(DyNacosConfig nacosConfig) throws NacosException {
        serverAddr=nacosConfig.getServerAddr();
        dataIds=nacosConfig.getDataId().split(",");
        group=nacosConfig.getGroup();
        timeoutMs=nacosConfig.getTimeoutMs();
        createService(serverAddr);
    }

    private ConfigService createService(String serverAddr) throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        configService = NacosFactory.createConfigService(properties);
        return configService;
    }

    public void doListener() throws NacosException {
        for (String dataId:dataIds){
            configService.addListener(dataId,group,new DyNacosListener());
        }
    }

    public Properties doPullConfig() throws NacosException, IOException {
        DyNacosPropertisMannager mannager=DyNacosPropertisMannager.Buider.getMannager();
        for (String dataId:dataIds){
            String content = configService.getConfig(dataId, group, timeoutMs);
            mannager.updateInputStream(dataId,content);
        }
        Properties properties=mannager.getNacosEvn();
        return properties;
    }

    public static DyNacosConfigService getNaocsConfigService() {
        return naocsConfigService;
    }

}
