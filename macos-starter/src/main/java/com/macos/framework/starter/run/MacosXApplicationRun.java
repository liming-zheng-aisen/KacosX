package com.macos.framework.starter.run;

import com.macos.ConsolePrint;
import com.macos.common.times.Timer;
import com.macos.framework.context.ApplicationContextImpl;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.core.load.clazz.ApplicationClassLoader;
import com.macos.framework.core.load.conf.ConfigurationLoader;
import com.macos.framework.core.load.ioc.IocLoader;
import com.macos.framework.core.load.manager.LoaderManager;
import com.macos.framework.starter.load.MacosXStarterLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 启动
 */
public class MacosXApplicationRun {

    static Logger logger= LoggerFactory.getLogger(MacosXApplicationRun.class);

    public static void run(Class main,String[] args) {

        ConsolePrint.printLogo(main);

        logger.info("开始执行..................");

        Timer timer=new Timer();

        try {
            //开始计时
            timer.doStart();

            ApplicationContextApi context= ApplicationContextImpl.Builder.getDySpringApplicationContext();

            context.registerBean("MacosXTimer",timer);

            logger.info("配置LoaderManager加载管理器");
            LoaderManager loaderManager=new LoaderManager();

            logger.info("注册一个配置文件ConfigurationLoader加载器");
            loaderManager.registerLoader(new ConfigurationLoader());


            logger.info("注册一个ApplicationClassLoader类加载器");
            loaderManager.registerLoader(new ApplicationClassLoader());

            logger.info("注册一个MacosXStarterLoader启动加载器");
            loaderManager.registerLoader(new MacosXStarterLoader());

            logger.info("注册一个IocLoader注入加载器");
            loaderManager.registerLoader(new IocLoader());

            logger.info("管理器注册结束，执行加载器的doLoad程序");
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
