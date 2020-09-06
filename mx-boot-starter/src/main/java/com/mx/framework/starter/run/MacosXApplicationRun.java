package com.mx.framework.starter.run;

import com.mx.console.ConsolePrint;
import com.mx.common.times.Timer;
import com.mx.console.log.Logger;
import com.mx.console.log.factory.LoggerFactory;
import com.mx.framework.context.base.ApplicationContextApi;

import com.mx.framework.core.context.ApplicationContextImpl;
import com.mx.framework.core.listener.manager.MacosXListerManager;
import com.mx.framework.starter.work.MacosXApplicationWork;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 启动
 */
public class MacosXApplicationRun {

    private final static Logger log= LoggerFactory.buildLogger(MacosXApplicationRun.class);

    /**
     * 启动
     * @param main
     * @param args
     */
    public static void run(Class main,String[] args) {

        ConsolePrint.printLogo(main);

        log.info("开始执行..................");

        Timer timer=new Timer();

        try {
            //开始计时
            timer.doStart();

            ApplicationContextApi context= ApplicationContextImpl.Builder.getApplicationContext();

            context.registerBean("MXTimer",timer);

            MacosXApplicationWork.run(main,args);

            MacosXListerManager.noticeLister();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }
    private static void close(){
        System.exit(0);
    }





}
