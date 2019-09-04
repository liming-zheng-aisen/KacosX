package com.duanya.spring.framework.core.listener.api;


import com.duanya.spring.framework.context.base.DyApplicationContext;

/**
 * @Desc IDyLoadListener
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public interface IDyLoadListener {
   void notice(DyApplicationContext context);
}
