package com.duanya.spring.framework.starter.run;

import com.macos.println.DyConsolePrint;
import com.macos.println.common.times.DyTimer;
import com.macos.println.framework.context.spring.DySpringApplicationContext;
import com.macos.framework.core.load.DyClassLoader;
import com.macos.framework.core.load.DyConfigurationLoader;
import com.macos.framework.core.load.DyIocLoader;
import com.macos.framework.core.load.manager.DyLoaderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 启动
 */
public class BootApplicationRun {

    static Logger logger= LoggerFactory.getLogger(com.duanya.spring.framework.starter.run.BootApplicationRun.class);

    public static void run(Class main) {

        DyConsolePrint.printLogo(main);

        logger.info("DyBootApplicationRun执行run方法");

        DyTimer dyTimer=new DyTimer();

        try {
            //开始计时
            dyTimer.doStart();

            DySpringApplicationContext context=DySpringApplicationContext.Builder.getDySpringApplicationContext();

            context.registerBean("dyTimer",dyTimer);

            logger.info("配置DyLoaderManager加载管理器");
            DyLoaderManager loaderManager=new DyLoaderManager();

            logger.info("注册一个配置文件加载器");
            loaderManager.registerLoader(new DyConfigurationLoader());


            logger.info("注册一个类加载器，用于加载目标项目的类");
            loaderManager.registerLoader(new DyClassLoader());

            logger.info("注册一个dyboot启动加载器");
            loaderManager.registerLoader(new com.duanya.spring.framework.starter.load.BootStarterLoader());

            logger.info("注册一个IOC注入加载器");
            loaderManager.registerLoader(new DyIocLoader());

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
