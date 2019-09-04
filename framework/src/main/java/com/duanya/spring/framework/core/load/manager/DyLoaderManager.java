package com.duanya.spring.framework.core.load.manager;

import com.duanya.spring.framework.core.listener.api.manager.DyLoaderListerManager;
import com.duanya.spring.framework.core.load.DyBeanLoad;
import com.duanya.spring.framework.core.load.DyClassLoader;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import com.duanya.spring.framework.core.load.DyIocLoader;


/**
 * @Desc DyLoaderManager
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyLoaderManager {

    private  DyBeanLoad  fristLoader;

    private  DyBeanLoad  lastLoader;

    public  void registerLoader(DyBeanLoad loader){
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
        fristLoader.load(c);
        DyLoaderListerManager.noticeLister();
    }

    /**
     * 默认加载
     * @throws Exception
     */
    public  void doDefalultLoad() throws Exception {
        fristLoader=null;
        lastLoader=null;
        registerLoader(new DyConfigurationLoader());
        registerLoader(new DyClassLoader());
        registerLoader(new DyIocLoader());
        doLoad(this.getClass());
    }

    /**
     * 获取加载器
     * @return
     */
    public DyBeanLoad getLoader(){return fristLoader;}


}
