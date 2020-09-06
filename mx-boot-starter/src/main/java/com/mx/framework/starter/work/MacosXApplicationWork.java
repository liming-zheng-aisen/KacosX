package com.mx.framework.starter.work;

import com.mx.core.work.ApplicationWork;
import com.mx.framework.starter.combiner.MacosXApplicationCombiner;
import com.mx.framework.starter.combiner.MacosXApplicationStarterCombiner;


/**
 * @Desc 程序工作环境
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

        doMXApplication(main,main,args);

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
    public static void doMXApplication(Class main , Class handlerClass , String[] args) throws Exception {
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
