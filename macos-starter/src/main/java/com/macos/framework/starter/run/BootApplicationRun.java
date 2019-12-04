package com.macos.framework.starter.run;

import com.macos.ConsolePrint;
import com.macos.common.times.Timer;
import com.macos.framework.context.ApplicationContextImpl;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.core.load.ApplicationClassLoader;
import com.macos.framework.core.load.ConfigurationLoader;
import com.macos.framework.core.load.IocLoader;
import com.macos.framework.core.load.manager.LoaderManager;
import com.macos.framework.starter.load.BootStarterLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 启动
 */
public class BootApplicationRun {

    static Logger logger= LoggerFactory.getLogger(BootApplicationRun.class);

    public static void run(Class main) {

        ConsolePrint.printLogo(main);

        logger.info("DyBootApplicationRun执行run方法");

        Timer dyTimer=new Timer();

        try {
            //开始计时
            dyTimer.doStart();

            ApplicationContextApi context= ApplicationContextImpl.Builder.getDySpringApplicationContext();

            context.registerBean("dyTimer",dyTimer);

            logger.info("配置DyLoaderManager加载管理器");
            LoaderManager loaderManager=new LoaderManager();

            logger.info("注册一个配置文件加载器");
            loaderManager.registerLoader(new ConfigurationLoader());


            logger.info("注册一个类加载器，用于加载目标项目的类");
            loaderManager.registerLoader(new ApplicationClassLoader());

            logger.info("注册一个dyboot启动加载器");
            loaderManager.registerLoader(new BootStarterLoader());

            logger.info("注册一个IOC注入加载器");
            loaderManager.registerLoader(new IocLoader());

            logger.info("启动DyLoaderManager加载管理器");
            loaderManager.doLoad(main);

        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }
    private static void close(){
        System.exit(0);
    }

}
