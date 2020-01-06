package com.macos.framework.core.load.clazz;

import com.macos.framework.annotation.MacosXScanner;
import com.macos.framework.core.handle.ConfigurationHandle;
import com.macos.framework.core.handle.MacosXScannerHandle;
import com.macos.framework.core.load.abs.BeanLoad;
import lombok.extern.slf4j.Slf4j;


/**
 * @author zheng.liming
 * @date 2019/8/20
 * @description 主要任务是加载
 */
@Slf4j
@SuppressWarnings("all")
public  class ApplicationClassLoader extends BeanLoad {

    public ApplicationClassLoader(){
    }

    /**
     * 根据当前位置，加载程序中的class
     * @param c
     * @throws Exception
     */
    @Override
    public  void load(Class c) throws Exception{
        MacosXScannerHandle macosXScannerHandle = new MacosXScannerHandle();
        macosXScannerHandle.doHandle(c);
        //ConfigurationHandle.registerHandle(MacosXScanner.class,macosXScannerHandle);
        ConfigurationHandle configurationHandle = new ConfigurationHandle();
        configurationHandle.doHandle(c);
    }



}
