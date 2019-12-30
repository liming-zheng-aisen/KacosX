//package com.macos.framework.core.listener.manager;
//
//import com.macos.framework.core.listener.api.LoadListener;
//import com.macos.framework.core.load.manager.LoaderManager;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//import java.util.concurrent.*;
//
///**
// * @Desc 监听加载器管理器
// * @Author Zheng.LiMing
// * @Date 2019/9/4
// */
//@SuppressWarnings("all")
//public class LoaderListerManager {
//
//    private final static Logger log=LoggerFactory.getLogger(LoaderManager.class);
//
//    private static List<LoadListener> listeners=new ArrayList<>();
//
//    private static ExecutorService executorService;
//
//    static {
//        /**
//         * 初始化线程池
//         */
//        int coreSize = Runtime.getRuntime().availableProcessors();
//        executorService = new ThreadPoolExecutor(
//                coreSize,
//                coreSize+2,
//                5L,
//                TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(1024),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.CallerRunsPolicy());
//    }
//
//    public static void registerLister(LoadListener loadListener){
//        listeners.add(loadListener);
//    }
//
//    /**
//     * 异步通知监听器容器初始化完成
//     */
//    public static void  noticeLister(){
//        if (listeners.size()>0){
//            log.info("开始执行监听通知");
//            for (LoadListener listener:listeners) {
//                executorService.execute (new Thread(){
//                    @Override
//                    public void run() {
//                        listener.notice();
//                    }
//                });
//            }
//        }
//
//    }
//
//    /**
//     * 异步通知监听器更新配置
//     * @param env
//     */
//    public static void  updateLister(Properties env){
//        if (listeners.size()>0){
//            log.info("更新配置");
//            for (LoadListener listener:listeners) {
//                executorService.execute(new Thread(){
//                    @Override
//                    public void run() {
//                        listener.update(env);
//                    }
//                });
//            }
//        }
//
//    }
//
//}
