package com.duanya.spring.framework.core.load;

import com.duanya.spring.framework.core.scanner.impl.DyScannerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zheng.liming
 * @date 2019/8/20
 * @description 主要任务是加载
 */
public class DyClassLoader implements DyBeanLoad {
    private static final Logger log = LoggerFactory.getLogger(DyClassLoader.class);

    private static List<Class> classContainer;

    private static String DY_PACKAGE="com.duanya.spring.framework.jdbc";

    private static boolean isLoadDefault=false;

    public  DyClassLoader(){
        classContainer=new ArrayList<>();
        if (isLoadDefault==false){
            try {
                load(DY_PACKAGE);
            } catch (IOException e) {
               log.error(e.getMessage());
            }
            isLoadDefault=true;
        }
    }

    @Override
    public  void load(Class c) {

        //根据主入口的文件加载同级目录或子目录下的class文件
        //获取类全路径
        String basePackage=c.getPackage().getName();
        DyScannerImpl dyScanner=new DyScannerImpl(basePackage);
        List<String> list= null;
        try {
            list = dyScanner.doScanner();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addResult(list);
        log.info("DyClassLoader已经加载"+basePackage+"下面的类");
    }

    public void load(String packageName) throws IOException {
        DyScannerImpl dyScanner=new DyScannerImpl(packageName);
        List<String> list = dyScanner.doScanner();
        addResult(list);
        log.info("DyClassLoader已经加载"+packageName+"下面的类");
    }

    private void addResult(List<String> result){
        result.stream().forEach(item-> {
            try {
                classContainer.add(Class.forName(item));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public static List<Class> getClassContainer() {
        return classContainer;
    }
}
