package com.mx.console.log.lazy;

import com.mx.console.ConsolePrint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Desc LoggerLazy
 * @Author Zheng.LiMing
 * @Date 2020/8/20
 */
public class LoggerLazy {

    private static Queue<LogBody> logBodyQueue = new LinkedBlockingQueue<>();

    private final static SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        new Thread(()->{
            while (true){
                LogBody logBody =  logBodyQueue.poll();
                if (null != logBody){
                    switch (logBody.getLogType()){
                        case INFO:
                            ConsolePrint.info(formatTitle(logBody),logBody.getMsg());
                            break;
                        case WARN:
                            ConsolePrint.warn(formatTitle(logBody),logBody.getMsg());
                            break;
                        case DEBUG:
                            ConsolePrint.debug(formatTitle(logBody),logBody.getMsg());
                            break;
                        case ERROR:
                            ConsolePrint.error(formatTitle(logBody),logBody.getMsg());
                        break;
                        case SUCCESS:
                            ConsolePrint.success(formatTitle(logBody),logBody.getMsg());
                            break;
                        default:
                            break;
                    }
                }
            }
        }).start();
    }

    private static synchronized String formatTitle(LogBody logBody){
        return "["+logBody.getThreadName()+"] "+"["+simpleDateFormat.format(new Date())+"] "+logBody.getTitle();
    }

    public static void pushLog(LogBody logBody){
        logBodyQueue.add(logBody);
    }
}
