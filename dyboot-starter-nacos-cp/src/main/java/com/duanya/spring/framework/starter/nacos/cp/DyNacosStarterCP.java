package com.duanya.spring.framework.starter.nacos.cp;

import com.duanya.spring.framework.annotation.DyBootApplicationStarter;
import com.duanya.spring.framework.core.listener.api.manager.DyLoaderListerManager;
import com.duanya.spring.framework.starter.DyDefaultStarter;
import com.duanya.spring.framework.starter.nacos.cp.listener.DybootNacosStarterListener;

import java.util.Properties;

/**
 * @Desc nacos服务启动订阅与发现
 * @Author Zheng.LiMing
 * @Date 2019/9/12
 */
@DyBootApplicationStarter(scannerPath = {"com.duanya.spring.framework.nacos"},order = Integer.MAX_VALUE-200)
public class DyNacosStarterCP implements DyDefaultStarter {
    @Override
    public void doStart(Properties evn, Class cl) throws Exception {

        DyLoaderListerManager.registerLister(new DybootNacosStarterListener());
    }

}
