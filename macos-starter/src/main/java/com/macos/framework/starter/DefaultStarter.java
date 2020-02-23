package com.macos.framework.starter;

import com.macos.framework.core.env.ApplicationENV;

/**
 * @Desc DyDefaultStarter
 * @Author Zheng.LiMing
 * @Date 2019/9/3
 */
public interface DefaultStarter {

   void doStart(ApplicationENV env, Class main , String[] args) throws Exception;

}
