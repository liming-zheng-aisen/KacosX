package com.duanya.spring.framework.core.scanner.impl;

import com.duanya.spring.commont.util.StringUtils;
import com.duanya.spring.framework.core.scanner.api.IDyScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 扫描
 */
public class DyScannerImpl implements IDyScanner {

    private static final Logger log = LoggerFactory.getLogger(DyScannerImpl.class);

    private String basePackage;

    private ClassLoader cl;


    public DyScannerImpl(String basePackage) {
        this.basePackage = basePackage;
        this.cl = getClass().getClassLoader();

    }

    //初始化類的包和根路徑
    public DyScannerImpl(String basePackage, ClassLoader cl) {
        this.basePackage = basePackage;
        this.cl = cl;
    }
    /**
     * 获取包下全部的类
     * @return
     * @throws IOException
     */
    @Override
    public List<String> doScanner() throws IOException {
        log.info("DyScannerImpl开始扫描包{}下的所有类",basePackage);
        List<String> dsList=doScan(basePackage, new ArrayList<String>());
        return dueResult(dsList);
    }

    /**
     * 处理结果，在jar和开发时数据的格式有所不同
     * @param result
     * @return
     */
    private List<String> dueResult(List<String> result){
        List<String> ls=new ArrayList<>();
        for (String str:result) {
            if (str.indexOf("/")>0){
                String clzz= str.substring(str.lastIndexOf(".")+1);
                clzz=clzz.replace("/",".");
                System.out.println(clzz);
                ls.add(clzz);
            }
        }
        if (ls.size()==0){
            ls=result;
        }

        return ls;
    }
    /**
     *
     *掃描包
     * @param basePackage
     * @param nameList
     * @return
     * @throws IOException
     */
    private List<String> doScan(String basePackage, List<String> nameList) throws IOException {


        String splashPath = StringUtils.dotToSplash(basePackage);


        URL url = cl.getResource(splashPath);
        String filePath = StringUtils.getRootPath(url);

        List<String> names = null;
        if (isJarFile(filePath)) {

            names = readFromJarFile(filePath, splashPath);
        } else {


            names = readFromDirectory(filePath);
        }

        for (String name : names) {
            if (isClassFile(name)) {

                nameList.add(toFullyQualifiedName(name, basePackage));
            } else {

                doScan(basePackage + "." + name, nameList);
            }
        }

        return nameList;
    }


    private String toFullyQualifiedName(String shortName, String basePackage) {
        StringBuilder sb = new StringBuilder(basePackage);
        sb.append('.');
        sb.append(StringUtils.trimExtension(shortName));
        return sb.toString();
    }

    private List<String> readFromJarFile(String jarPath, String splashedPackageName) throws IOException {

        JarInputStream jarIn = new JarInputStream(new FileInputStream(jarPath));
        JarEntry entry = jarIn.getNextJarEntry();

        List<String> nameList = new ArrayList<String>();
        while (null != entry) {
            String name = entry.getName();
            if (name.startsWith(splashedPackageName) && isClassFile(name)) {
                nameList.add(name);
            }

            entry = jarIn.getNextJarEntry();
        }
        return nameList;
    }

    private List<String> readFromDirectory(String path) {
        File file = new File(path);
        String[] names = file.list();

        if (null == names) {
            return null;
        }

        return Arrays.asList(names);
    }

    private boolean isClassFile(String name) {
        return name.endsWith(".class");
    }

    private boolean isJarFile(String name) {
        return name.endsWith(".jar");
    }

}
