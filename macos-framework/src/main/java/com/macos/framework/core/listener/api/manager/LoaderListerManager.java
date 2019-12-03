package com.macos.framework.core.listener.api.manager;

import com.macos.framework.core.listener.api.LoadListener;
import com.macos.framework.core.load.manager.LoaderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * @Desc DyLoaderListerManager
 * @Author Zheng.LiMing
 * @Date 2019/9/4 加载器监听
 */
public class LoaderListerManager {
    private final static Logger log=LoggerFactory.getLogger(LoaderManager.class);

    private static List<LoadListener> listeners=new ArrayList<>();

    private static ExecutorService executorService;

    static {
        int coreSize = Runtime.getRuntime().availableProcessors();
        executorService = new ThreadPoolExecutor(
                coreSize,
                coreSize+2,
                5L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(512),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void registerLister(LoadListener loadListener){
        listeners.add(loadListener);
    }

    public static void  noticeLister(){
        if (listeners.size()>0){
            log.info("开始执行监听通知");
            for (LoadListener listener:listeners) {
                executorService.execute (new Thread(){
                    @Override
                    public void run() {
                        listener.notice();
                    }
                });
            }
        }

    }

    public static void  updateLister(Properties env){
        if (listeners.size()>0){
            log.info("更新配置");
            for (LoadListener listener:listeners) {
                executorService.execute(new Thread(){
                    @Override
                    public void run() {
                        listener.update(env);
                    }
                });
            }
        }

    }

}
