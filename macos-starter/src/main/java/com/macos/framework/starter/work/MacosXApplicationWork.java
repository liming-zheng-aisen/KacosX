package com.macos.framework.starter.work;

import com.macos.core.work.ApplicationWork;
import com.macos.framework.starter.combiner.AutoConfigurationCombiner;
import com.macos.framework.starter.combiner.MacosXApplicationCombiner;
import com.macos.framework.starter.combiner.MacosXApplicationStarterCombiner;


/**
 * @Desc MacosXApplicationWork
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class MacosXApplicationWork extends ApplicationWork {

    /**
     * 开始工作
     * @param main
     * @param args
     */
    public static void run(Class main,String[] args) throws Exception {

        doMacosXApplication(main,main,args);

        classLoader(main,args);

        doMacosXApplicationStarter(main,main,args);

        work(main,args);
    }

    /**
     * 应用程序
     * @param main
     * @param handlerClass
     * @param args
     * @throws Exception
     */
    public static void doMacosXApplication(Class main , Class handlerClass , String[] args) throws Exception {
        MacosXApplicationCombiner.getCombiner()
                .getHandler()
                .doHandle(main,handlerClass,args);
    }



    /***
     * starter模块
     * @param main
     * @param handlerClass
     * @param args
     * @throws Exception
     */
    public static void doMacosXApplicationStarter(Class main , Class handlerClass , String[] args) throws Exception {
        MacosXApplicationStarterCombiner.getCombiner()
                .getHandler()
                .doHandle(main,handlerClass,args);
    }
}
