package com.duanya.spring.framework.core.load;

import com.duanya.spring.framework.core.scanner.impl.DyScannerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zheng.liming
 * @date 2019/8/20
 * @description 主要任务是加载
 */
public class DyClassLoader {

    private static List<Class> classContainer;

    public  DyClassLoader(){
        classContainer=new ArrayList<>();
    }
    public  void load(Class c) {
        //根据主入口的文件加载同级目录或子目录下的class文件
        //获取类全路径
        String basePackage=c.getPackage().getName();
        //获取截取包路径
        basePackage=basePackage.substring(0,basePackage.lastIndexOf("."));
        DyScannerImpl dyScanner=new DyScannerImpl();
        List<String> list= dyScanner.doScanner(basePackage);
        addResult(list);
    }
    public void load(String packageName){
        DyScannerImpl dyScanner=new DyScannerImpl();
        List<String> list = dyScanner.doScanner(packageName);
        addResult(list);
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
