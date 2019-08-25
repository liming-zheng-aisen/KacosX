package com.duanya.spring.framework.core.scanner.impl;

import com.duanya.spring.framework.core.scanner.api.IDyScanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 扫描
 */
public class DyScannerImpl implements IDyScanner {

    private List<String> result;
    private String classPath=this.getClass().getClassLoader().getResource("").getPath();

    @Override
    public synchronized List<String> doScanner(String scanPackage){
        result = new ArrayList<String>();
        findClass(scanPackage);
        return result;
    }

    private void findClass(String scanPackage) {
        String url=classPath+scanPackage.replaceAll("\\.","/");
        File classPath=new File(url);
        for (File f:classPath.listFiles()){
            if (f.isDirectory()){
                findClass(scanPackage+"."+f.getName());
            }else {
                if (!f.getName().endsWith(".class")) {
                    continue;
                }
                String className=scanPackage+"."+f.getName().replace(".class","").trim();
                result.add(className);
            }

        }
    }


}
