package com.mx.nacos.config.service;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.mx.nacos.config.NacosConfig;
import com.mx.nacos.config.NacosPropertisMannager;
import com.mx.nacos.config.listener.NacosListener;

import java.io.IOException;
import java.util.Properties;

/**
 * @Desc DyNacosConfigService
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */

public class NacosConfigService {

    private  ConfigService configService;

    private  String serverAddr;

    private  String[] dataIds;

    private  String group;

    private  Integer timeoutMs;

    private static NacosConfigService naocsConfigService=new NacosConfigService();

    private NacosConfigService(){

    }


    public void init(NacosConfig nacosConfig) throws NacosException {
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
            configService.addListener(dataId,group,new NacosListener());
        }
    }

    public Properties doPullConfig() throws NacosException, IOException {
        NacosPropertisMannager mannager= NacosPropertisMannager.Buider.getMannager();
        for (String dataId:dataIds){
            String content = configService.getConfig(dataId, group, timeoutMs);
            mannager.updateInputStream(dataId,content);
        }
        Properties properties=mannager.getNacosEvn();
        return properties;
    }

    public static NacosConfigService getNaocsConfigService() {
        return naocsConfigService;
    }

}
