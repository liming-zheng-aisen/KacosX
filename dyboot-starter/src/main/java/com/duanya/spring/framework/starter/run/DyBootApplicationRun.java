package com.duanya.spring.framework.starter.run;

import com.duanya.spring.common.times.DyTimer;
import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
import com.duanya.spring.framework.core.load.DyClassLoader;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import com.duanya.spring.framework.core.load.DyIocLoader;
import com.duanya.spring.framework.core.load.manager.DyLoaderManager;
import com.duanya.spring.framework.starter.load.DyBootStarterLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 启动
 */
public class DyBootApplicationRun {

    static Logger logger= LoggerFactory.getLogger(DyBootApplicationRun.class);

    public static void run(Class main) {
        //计时器
        DyTimer dyTimer=new DyTimer();
        try {
            //开始计时
            dyTimer.doStart();
            //初始化一个加载管理器
            DyLoaderManager loaderManager=new DyLoaderManager();
            //注册一个配置文件加载器
            loaderManager.registerLoader(new DyConfigurationLoader());
            //注册一个dyboot启动加载器
            loaderManager.registerLoader(new DyBootStarterLoader());
            //注册一个类加载器，用于加载目标项目的类
            loaderManager.registerLoader(new DyClassLoader());
            //注册一个IOC注入加载器
            loaderManager.registerLoader(new DyIocLoader());
            //加载器管理器启动加载
            loaderManager.doLoad(main);

            DySpringApplicationContext context=new DySpringApplicationContext();
            context.registeredBean("dyTimer",dyTimer);

        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }
    private static void close(){
        System.exit(0);
    }

}
