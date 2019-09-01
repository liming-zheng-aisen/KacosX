package com.duanya.spring.framework.core.load;

import com.duanya.spring.framework.core.scanner.impl.DyScannerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zheng.liming
 * @date 2019/8/20
 * @description 主要任务是加载
 */
public class DyClassLoader implements DyBeanLoad {

    private static List<Class> classContainer;

    public  DyClassLoader(){
        classContainer=new ArrayList<>();
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
    }

    public void load(String packageName) throws IOException {
        DyScannerImpl dyScanner=new DyScannerImpl(packageName);
        List<String> list = dyScanner.doScanner();
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
