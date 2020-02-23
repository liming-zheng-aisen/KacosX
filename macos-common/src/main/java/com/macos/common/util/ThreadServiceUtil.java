package com.macos.common.util;

import java.util.concurrent.*;

/**
 * @author Aisen
 * @mail aisen.zheng.tech@linkkt.one
 * @creater 2020/1/2 16:59:48
 * @desc
 */
public class ThreadServiceUtil {
    private static ExecutorService executorService;

    static {
        /**
         * 初始化线程池
         */
        int coreSize = Runtime.getRuntime().availableProcessors();
        executorService = new ThreadPoolExecutor(
                coreSize,
                coreSize+2,
                600L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }


    public static Future submit(Runnable runnable){
       return executorService.submit(runnable);
    }


    public static void execute(Runnable runnable){
        executorService.execute(runnable);
    }


}
