package com.macos.framework.core.load.manager;

import com.macos.framework.core.listener.api.manager.LoaderListerManager;
import com.macos.framework.core.load.ApplicationClassLoader;
import com.macos.framework.core.load.BeanLoad;
import com.macos.framework.core.load.ConfigurationLoader;
import com.macos.framework.core.load.IocLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Desc DyLoaderManager
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class LoaderManager {

    private final static Logger log=LoggerFactory.getLogger(LoaderManager.class);

    private BeanLoad fristLoader;

    private BeanLoad lastLoader;

    public  void registerLoader(BeanLoad loader){

        if (null==fristLoader){
            fristLoader=loader;
            return;
        }

        if (null==lastLoader){
            fristLoader.setNextLoader(loader);
            lastLoader=loader;
        }else {
            lastLoader.setNextLoader(loader);
            lastLoader=loader;
        }
    }

    public  void doLoad(Class c) throws  Exception{

        log.info("开始执行DyBeanLoad加载器");
        fristLoader.load(c);

        log.info("调用DyLoaderListerManager监听管理器通知");

        LoaderListerManager.noticeLister();

    }

    /**
     * 默认加载
     * @throws Exception
     */
    public  void doDefalultLoad() throws Exception {
        fristLoader=null;
        lastLoader=null;
        registerLoader(new ConfigurationLoader());
        registerLoader(new ApplicationClassLoader());
        registerLoader(new IocLoader());
        doLoad(this.getClass());
    }

    /**
     * 获取加载器
     * @return
     */
    public BeanLoad getLoader(){return fristLoader;}


}
