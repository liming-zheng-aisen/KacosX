//package com.macos.framework.core.load.manager;
//
//import com.macos.framework.core.listener.manager.LoaderListerManager;
//import com.macos.framework.core.load.clazz.ApplicationClassLoader;
//import com.macos.framework.core.load.abs.BeanLoad;
//import com.macos.framework.core.load.conf.ConfigurationLoader;
//import com.macos.framework.core.load.ioc.IocLoader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
///**
// * @Desc 加载器管理
// * @Author Zheng.LiMing
// * @Date 2019/9/4
// */
//public class LoaderManager {
//
//    private final static Logger log=LoggerFactory.getLogger(LoaderManager.class);
//
//    /**
//     * 链首（加载器）
//     */
//    private BeanLoad fristLoader;
//    /**
//     * 链尾（加载器）
//     */
//    private BeanLoad lastLoader;
//
//    /**
//     * 注册加载器
//     * @param loader
//     */
//    public  void registerLoader(BeanLoad loader){
//        /**
//         * 链首为null的话，则将第一个加载器变成链首
//         */
//        if (null==fristLoader){
//            fristLoader=loader;
//            return;
//        }
//        /**
//         * 如果链尾为null，说明链首是的下一个加载器为null，先赋值给链首的下个加载器，同时也赋值给链尾
//         */
//        if (null==lastLoader){
//            fristLoader.setNextLoader(loader);
//            lastLoader=loader;
//        }else {
//            lastLoader.setNextLoader(loader);
//            lastLoader=loader;
//        }
//    }
//
//    /**
//     * 执行加载，并通知加载器监听器
//     * @param c
//     * @throws Exception
//     */
//    public  void doLoad(Class c) throws  Exception{
//        log.info("开始执行BeanLoad加载器");
//        fristLoader.load(c);
//        log.info("调用LoaderListerManager监听管理器通知");
//        LoaderListerManager.noticeLister();
//    }
//
//    /**
//     * 初始化默认加载器
//     * @throws Exception
//     */
//    public  void doDefalultLoad() throws Exception {
//        fristLoader=null;
//        lastLoader=null;
//        registerLoader(new ConfigurationLoader());
//        registerLoader(new ApplicationClassLoader());
//        registerLoader(new IocLoader());
//        doLoad(this.getClass());
//    }
//
//    /**
//     * 获取加载器
//     * @return
//     */
//    public BeanLoad getLoader(){return fristLoader;}
//
//
//}
