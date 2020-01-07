package com.macos.framework.core.handle;

import com.macos.framework.core.handle.base.BaseHandle;

/**
 * @Desc AutowiredHandle
 * @Author Zheng.LiMing
 * @Date 2020/1/8
 */
public class AutowiredHandle extends BaseHandle {
    @Override
    public boolean doHandle(Class target, String[] args) throws Exception {
        return false;
    }
}
