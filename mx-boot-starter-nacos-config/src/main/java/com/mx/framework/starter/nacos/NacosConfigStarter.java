package com.mx.framework.starter.nacos;

import com.mx.framework.annotation.boot.MacosXApplicationStarter;
import com.mx.framework.core.env.ApplicationENV;
import com.mx.framework.core.listener.manager.MacosXListerManager;
import com.mx.framework.starter.DefaultStarter;
import com.mx.nacos.config.listener.NacosStarterListener;

/**
 * @Desc 配置中心启动
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
@MacosXApplicationStarter(scannerPath = {"com.macos.nacos.config"},order = Integer.MIN_VALUE)
public class NacosConfigStarter implements DefaultStarter {
    @Override
    public void doStart(ApplicationENV env, Class main , String[] args) throws Exception {
        MacosXListerManager.registerLister(new NacosStarterListener());
    }



}
