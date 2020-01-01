package com.macos.framework.core.load.clazz;

import com.macos.common.scanner.impl.ScannerImpl;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.annotation.MacosXScanner;
import com.macos.framework.core.load.abs.BeanLoad;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.Set;

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

    }

    /**
     * 根据包名加载class
     * @param packageName
     * @throws Exception
     */
    public void load(String packageName) throws Exception {
        ScannerImpl scanner=new ScannerImpl();
        Set<Class> list = scanner.doScanner(packageName);
        BeanManager.registerClassBySet(list);
        log.info("ApplicationClassLoader已经加载"+packageName+"下面的类");
    }




}
