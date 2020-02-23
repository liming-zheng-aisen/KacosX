package com.macos.framework.starter.run;

import com.macos.ConsolePrint;
import com.macos.common.times.Timer;
import com.macos.framework.context.base.ApplicationContextApi;

import com.macos.framework.core.context.ApplicationContextImpl;
import com.macos.framework.core.listener.manager.MacosXListerManager;
import com.macos.framework.starter.work.MacosXApplicationWork;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 启动
 */
@Slf4j
public class MacosXApplicationRun {
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

            context.registerBean("MacosXTimer",timer);

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
