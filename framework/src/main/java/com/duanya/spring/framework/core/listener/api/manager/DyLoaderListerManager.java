package com.duanya.spring.framework.core.listener.api.manager;

import com.duanya.spring.framework.core.listener.api.IDyLoadListener;
import com.duanya.spring.framework.core.load.manager.DyLoaderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc DyLoaderListerManager
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyLoaderListerManager {

    private static List<IDyLoadListener> listeners=new ArrayList<>();

    private final static Logger log=LoggerFactory.getLogger(DyLoaderManager.class);


    public static void registerLister(IDyLoadListener loadListener){
        listeners.add(loadListener);
    }

    public static void  noticeLister(){
        if (listeners.size()>0){
            log.info("开始执行监听通知");
            for (IDyLoadListener listener:listeners) {
                (new Thread(){
                    @Override
                    public void run() {
                        listener.notice();
                    }
                }).start();
            }
        }

    }

}
