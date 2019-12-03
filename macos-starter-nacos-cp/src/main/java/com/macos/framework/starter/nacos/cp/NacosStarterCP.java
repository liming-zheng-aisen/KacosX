package com.macos.framework.starter.nacos.cp;


import com.macos.framework.annotation.MacosApplicationStarter;
import com.macos.framework.core.listener.api.manager.LoaderListerManager;
import com.macos.framework.starter.nacos.cp.listener.DybootNacosStarterListener;

import java.util.Properties;

/**
 * @Desc nacos服务启动订阅与发现
 * @Author Zheng.LiMing
 * @Date 2019/9/12
 */
@MacosApplicationStarter(scannerPath = {"com.macos.framework.nacos"},order = Integer.MAX_VALUE-200)
public class NacosStarterCP implements com.duanya.spring.framework.starter.DefaultStarter {
    @Override
    public void doStart(Properties evn, Class cl) throws Exception {
        LoaderListerManager.registerLister(new DybootNacosStarterListener());
    }

}
