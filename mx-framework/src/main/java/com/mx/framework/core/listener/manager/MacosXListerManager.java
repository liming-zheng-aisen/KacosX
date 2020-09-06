package com.mx.framework.core.listener.manager;

import com.mx.common.util.ThreadServiceUtil;
import com.mx.console.log.Logger;
import com.mx.console.log.factory.LoggerFactory;
import com.mx.framework.core.env.ApplicationENV;
import com.mx.framework.core.listener.api.MacosXListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc 监听加载器管理器
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
@SuppressWarnings("all")
public class MacosXListerManager {
    private final static Logger log = LoggerFactory.buildLogger(MacosXListerManager.class);

    private static List<MacosXListener> listeners=new ArrayList<>();

    public static void registerLister(MacosXListener loadListener){
        listeners.add(loadListener);
    }

    /**
     * 异步通知监听器容器初始化完成
     */
    public static void  noticeLister(){
        if (listeners.size()>0){
            log.info("开始执行监听通知");
            for (MacosXListener listener:listeners) {
                log.info("通知组件->{1}",listener.getClass().getName());
                listener.notice();
            }
        }
    }

    /**
     * 异步通知监听器更新配置
     * @param env
     */
    public static void  updateLister(ApplicationENV env){
        if (listeners.size()>0){
            log.info("更新配置");
            for (MacosXListener listener:listeners) {
                log.info("更新组件->{1}",listener.getClass().getName());
                ThreadServiceUtil.execute(new Thread(){
                    @Override
                    public void run() {
                        listener.update(env);
                    }
                });
            }
        }

    }

}
