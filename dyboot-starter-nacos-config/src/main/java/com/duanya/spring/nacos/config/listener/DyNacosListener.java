package com.duanya.spring.nacos.config.listener;

import com.alibaba.nacos.api.config.listener.Listener;
import com.duanya.spring.framework.core.listener.api.manager.DyLoaderListerManager;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import com.duanya.spring.framework.starter.nacos.DyNacosConfigStarter;
import com.duanya.spring.nacos.config.DyNacosPropertisMannager;
import com.duanya.spring.nacos.config.util.DyPropertiesUtil;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @Desc DyNacosListener
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class DyNacosListener implements Listener {

    private String dataId;

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        DyNacosPropertisMannager nacosPropertisMannager=DyNacosPropertisMannager.Buider.getMannager();
        try {
            nacosPropertisMannager.updateInputStream(dataId,configInfo);
            Properties nacosPropertis=nacosPropertisMannager.getNacosEvn();
            DyPropertiesUtil.cloneEvn(DyNacosConfigStarter.defaultEvn,nacosPropertis);
            DyConfigurationLoader.setEvn(nacosPropertis);
            DyLoaderListerManager.updateLister(DyConfigurationLoader.getEvn());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
