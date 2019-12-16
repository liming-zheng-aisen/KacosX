package com.macos.framework.core.load.clazz;

import com.macos.common.scanner.impl.ScannerImpl;
import com.macos.framework.core.bean.BeanManager;
import com.macos.framework.annotation.MacosXScanner;
import com.macos.framework.core.load.abs.BeanLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Set;

/**
 * @author zheng.liming
 * @date 2019/8/20
 * @description 主要任务是加载
 */
public  class ApplicationClassLoader extends BeanLoad {

    private static final Logger log = LoggerFactory.getLogger(ApplicationClassLoader.class);


    public ApplicationClassLoader(){
    }

    public ApplicationClassLoader(BeanLoad beanLoad){
        nextLoader=beanLoad;
    }

    /**
     * 根据当前位置，加载程序中的class
     * @param c
     * @throws Exception
     */
    @Override
    public  void load(Class c) throws Exception{
        if (c.isAnnotationPresent(MacosXScanner.class)){
            MacosXScanner macosXScanner = (MacosXScanner) c.getAnnotation(MacosXScanner.class);
            String[] packageNames= macosXScanner.packageNames();
            if (packageNames.length==0){
                log.error("扫描路径不允许为空或\"  \"");
                throw new Exception("扫描路径不允许为空或\"  \"");
            }
            for (String p:packageNames){
                try {
                    load(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
        //根据主入口的文件加载同级目录或子目录下的class文件
        //获取类全路径
        String basePackage=c.getPackage().getName();
        ScannerImpl dyScanner=new ScannerImpl();
        Set<Class> classSet= null;
        try {
            classSet = dyScanner.doScanner(basePackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
       BeanManager.registerClassBySet(classSet);
        log.info("ApplicationClassLoader已经加载"+basePackage+"下面的类");
        }
        if (null!=nextLoader){
            nextLoader.load(c);
        }
    }

    /**
     * 根据包名加载class
     * @param packageName
     * @throws Exception
     */
    public void load(String packageName) throws Exception {
        ScannerImpl dyScanner=new ScannerImpl();
        Set<Class> list = dyScanner.doScanner(packageName);
        BeanManager.registerClassBySet(list);
        log.info("ApplicationClassLoader已经加载"+packageName+"下面的类");
    }




}
