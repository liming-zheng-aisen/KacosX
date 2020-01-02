package com.macos.framework.core.load.manager;

import com.macos.framework.core.listener.manager.LoaderListerManager;
import com.macos.framework.core.load.clazz.ApplicationClassLoader;
import com.macos.framework.core.load.abs.BeanLoad;
import com.macos.framework.core.load.conf.ConfigurationLoader;
import com.macos.framework.core.load.ioc.IocLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;


/**
 * @Desc 加载器管理
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
@Slf4j
public class LoaderManager {

    private Set<BeanLoad> beanLoads = new HashSet<>();

    /**
     * 注册加载器
     * @param loader
     */
    public  void registerLoader(BeanLoad loader){
       beanLoads.add(loader);
       log.info("注册{}加载器",loader.getClass());
    }

    /**
     * 执行加载，并通知加载器监听器
     * @param c
     * @throws Exception
     */
    public  void doLoad(Class c) throws  Exception{
       for (BeanLoad beanLoad : beanLoads){
           log.info("开始执行{}加载器",beanLoad.getClass());
           beanLoad.load(c);
       }
        LoaderListerManager.noticeLister();
    }

    /**
     * 初始化默认加载器
     * @throws Exception
     */
    public  void doDefalultLoad() throws Exception {
        registerLoader(new ConfigurationLoader());
        registerLoader(new ApplicationClassLoader());
        registerLoader(new IocLoader());
        doLoad(this.getClass());
    }


}
