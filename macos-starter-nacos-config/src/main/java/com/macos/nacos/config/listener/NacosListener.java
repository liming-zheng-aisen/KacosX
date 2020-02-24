package com.macos.nacos.config.listener;

import com.alibaba.nacos.api.config.listener.Listener;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.core.listener.manager.MacosXListerManager;
import com.macos.nacos.config.NacosPropertisMannager;

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

    private static BeanManager beanManager = new BeanManager();


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
            ApplicationENV env = (ApplicationENV) beanManager.getBean(null,ApplicationENV.class);
            env.updateElementsAndKeyCache(nacosPropertis);
            MacosXListerManager.updateLister(env);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
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
