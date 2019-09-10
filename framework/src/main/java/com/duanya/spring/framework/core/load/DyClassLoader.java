package com.duanya.spring.framework.core.load;

import com.duanya.spring.common.scanner.impl.DyScannerImpl;
import com.duanya.spring.framework.annotation.DyScanner;
import com.duanya.spring.framework.core.bean.factory.bean.manager.DyBeanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

/**
 * @author zheng.liming
 * @date 2019/8/20
 * @description 主要任务是加载
 */
public  class DyClassLoader extends DyBeanLoad {

    private static final Logger log = LoggerFactory.getLogger(DyClassLoader.class);


    public  DyClassLoader(){
    }

    public  DyClassLoader(DyBeanLoad beanLoad){
        nextLoader=beanLoad;
    }

    @Override
    public  void load(Class c) throws Exception{
        if (c.isAnnotationPresent(DyScanner.class)){
            DyScanner scanner= (DyScanner) c.getAnnotation(DyScanner.class);
            String[] packageNames=scanner.packageNames();
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
        DyScannerImpl dyScanner=new DyScannerImpl();
        Set<Class> classSet= null;
        try {
            classSet = dyScanner.doScanner(basePackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DyBeanManager.registerClassBySet(classSet);
        log.info("DyClassLoader已经加载"+basePackage+"下面的类");
        }
        if (null!=nextLoader){
            nextLoader.load(c);
        }


    }

    public void load(String packageName) throws Exception {
        DyScannerImpl dyScanner=new DyScannerImpl();
        Set<Class> list = dyScanner.doScanner(packageName);
        DyBeanManager.registerClassBySet(list);
        log.info("DyClassLoader已经加载"+packageName+"下面的类");
    }




}
