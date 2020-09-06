package com.mx.framework.starter.nacos.cp;


import com.mx.framework.annotation.boot.MacosXApplicationStarter;
import com.mx.framework.core.env.ApplicationENV;
import com.mx.framework.core.listener.manager.MacosXListerManager;
import com.mx.framework.starter.DefaultStarter;
import com.mx.framework.starter.nacos.cp.listener.DybootNacosStarterListener;


/**
 * @Desc nacos服务启动订阅与发现
 * @Author Zheng.LiMing
 * @Date 2019/9/12
 */
@MacosXApplicationStarter(scannerPath = {"com.macos.framework.nacos"},order = Integer.MAX_VALUE-200)
public class NacosStarterCP implements DefaultStarter {
    @Override
    public void doStart(ApplicationENV env, Class main , String[] args) throws Exception {
        MacosXListerManager.registerLister(new DybootNacosStarterListener());
    }

}
