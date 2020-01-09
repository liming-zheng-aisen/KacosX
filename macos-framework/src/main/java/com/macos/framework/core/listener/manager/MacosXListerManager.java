package com.macos.framework.core.listener.manager;

import com.macos.framework.core.listener.api.MacosXListener;
import com.macos.framework.core.util.ThreadServiceUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Desc 监听加载器管理器
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
@SuppressWarnings("all")
@Slf4j
public class MacosXListerManager {


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
                ThreadServiceUtil.execute (new Thread(){
                    @Override
                    public void run() {
                        listener.notice();
                    }
                });
            }
        }
    }

    /**
     * 异步通知监听器更新配置
     * @param env
     */
    public static void  updateLister(Properties env){
        if (listeners.size()>0){
            log.info("更新配置");
            for (MacosXListener listener:listeners) {
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
