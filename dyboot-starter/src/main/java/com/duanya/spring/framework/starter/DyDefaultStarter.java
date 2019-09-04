package com.duanya.spring.framework.starter;

import com.duanya.spring.framework.context.spring.DySpringApplicationContext;

import java.util.Properties;

/**
 * @Desc DyDefaultStarter
 * @Author Zheng.LiMing
 * @Date 2019/9/3
 */
public interface DyDefaultStarter {

   void doStart(Properties evn, Class cl, DySpringApplicationContext content) throws Exception;

}
