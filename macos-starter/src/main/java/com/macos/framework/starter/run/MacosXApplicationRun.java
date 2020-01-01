package com.macos.framework.starter.run;

import com.macos.ConsolePrint;
import com.macos.common.times.Timer;
import com.macos.framework.context.impl.ApplicationContextImpl;
import com.macos.framework.context.base.ApplicationContextApi;

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

            ApplicationContextApi context= ApplicationContextImpl.Builder.getApplicationContext();

            context.registerBean("MacosXTimer",timer);


        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }
    private static void close(){
        System.exit(0);
    }

}
