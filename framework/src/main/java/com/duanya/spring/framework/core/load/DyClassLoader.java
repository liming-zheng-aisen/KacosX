package com.duanya.spring.framework.core.load;

import com.duanya.spring.common.scanner.impl.DyScannerImpl;
import com.duanya.spring.framework.annotation.DyScanner;
import com.duanya.spring.framework.core.bean.factory.bean.manager.DyBeanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

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
        DyScannerImpl dyScanner=new DyScannerImpl(basePackage);
        List<String> list= null;
        try {
            list = dyScanner.doScanner(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DyBeanManager.registerClassByClassString(list);
        log.info("DyClassLoader已经加载"+basePackage+"下面的类");
        }
        if (null!=nextLoader){
            nextLoader.load(c);
        }


    }

    public void load(String packageName) throws IOException {
        DyScannerImpl dyScanner=new DyScannerImpl(packageName);
        List<String> list = dyScanner.doScanner(true);
        DyBeanManager.registerClassByClassString(list);
        log.info("DyClassLoader已经加载"+packageName+"下面的类");
    }




}
