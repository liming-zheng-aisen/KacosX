package com.macos.framework.core.handle;

import com.macos.framework.annotation.Configuration;
import com.macos.framework.core.handle.base.BaseHandle;

/**
 * @Desc ConfigurationHandle
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
public class ConfigurationHandle implements BaseHandle {

    @Override
    public void doHandle(Class c){
        if (c.isAnnotationPresent(Configuration.class)){

        }
    }
}
