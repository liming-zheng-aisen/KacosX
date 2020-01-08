package com.macos.nacos.config.listener;

import com.alibaba.nacos.api.config.listener.Listener;
import com.macos.framework.core.listener.manager.MacosXListerManager;
import com.macos.framework.core.load.conf.PropertiesFileLoader;
import com.macos.framework.starter.nacos.NacosConfigStarter;
import com.macos.nacos.config.NacosPropertisMannager;
import com.macos.nacos.config.util.PropertiesUtil;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @Desc DyNacosListener
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class NacosListener implements Listener {

    private String dataId;

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        NacosPropertisMannager nacosPropertisMannager = NacosPropertisMannager.Buider.getMannager();
        try {
            nacosPropertisMannager.updateInputStream(dataId,configInfo);
            Properties nacosPropertis=nacosPropertisMannager.getNacosEvn();
            PropertiesUtil.cloneEvn(NacosConfigStarter.defaultEvn,nacosPropertis);
            PropertiesFileLoader.getEvn().clear();
            PropertiesUtil.cloneEvn(nacosPropertis, PropertiesFileLoader.getEvn());
            MacosXListerManager.updateLister(PropertiesFileLoader.getEvn());
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
