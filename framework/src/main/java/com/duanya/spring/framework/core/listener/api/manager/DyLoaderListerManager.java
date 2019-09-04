package com.duanya.spring.framework.core.listener.api.manager;

import com.duanya.spring.framework.context.base.DyApplicationContext;
import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
import com.duanya.spring.framework.core.listener.api.IDyLoadListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc DyLoaderListerManager
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyLoaderListerManager {

    private static List<IDyLoadListener> listeners=new ArrayList<>();

    private static DyApplicationContext context;

    public static void registerLister(IDyLoadListener loadListener){
        listeners.add(loadListener);
    }

    public static void  noticeLister(){

        if (listeners.size()>0){
            listeners.stream().forEach(listener->(new Thread(){
                @Override
                public void run() {
                   listener.notice(Builder.getContext());
                }
            }).start());
        }
    }

    public static  class Builder{
        public static DyApplicationContext getContext(){
            if (context==null){
                context=new DySpringApplicationContext();
            }
            return context;
        }
    }

}
