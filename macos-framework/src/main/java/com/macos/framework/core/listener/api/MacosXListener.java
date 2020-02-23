package com.macos.framework.core.listener.api;


import com.macos.framework.core.env.ApplicationENV;


/**
 * @Desc 监听加载器
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public interface MacosXListener {
   /**加载器加载完成后通知*/
   void notice();
   /**配置发生变化时通知*/
   void update(ApplicationENV evn);
}
