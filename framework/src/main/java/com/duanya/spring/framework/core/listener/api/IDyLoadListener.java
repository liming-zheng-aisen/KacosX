package com.duanya.spring.framework.core.listener.api;


import java.util.Properties;

/**
 * @Desc IDyLoadListener
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public interface IDyLoadListener {
   void notice();
   void update(Properties evn);
}
