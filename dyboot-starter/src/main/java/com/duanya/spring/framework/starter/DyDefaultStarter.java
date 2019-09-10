package com.duanya.spring.framework.starter;

import java.util.Properties;

/**
 * @Desc DyDefaultStarter
 * @Author Zheng.LiMing
 * @Date 2019/9/3
 */
public interface DyDefaultStarter {

   void doStart(Properties evn, Class cl) throws Exception;

}
