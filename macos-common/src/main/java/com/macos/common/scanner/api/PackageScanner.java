package com.macos.common.scanner.api;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Desc ClassLoader
 * @Author Zheng.LiMing
 * @Date 2019/9/6
 */
@SuppressWarnings("all")
public abstract class PackageScanner {

    private ClassLoader classLoader;


    public PackageScanner() {
    }

    public abstract void dealClass(Class<?> cl);


    public PackageScanner scanRoot(String packName) {
        //创建类加载器，不懂的可以百度学习下，以及classLoader.getResources
        this.classLoader = Thread.currentThread().getContextClassLoader();
        //packageName存放包名
        String packageName = "";
        //如果不含类即不是com.mec.util.test.java格式
        if(!packName.contains(".java")) {
            //将包名字变成路径
            packageName = packName.replace(".", "/");
        } else {
            //是com.mec.util.test.java格式
            //将路径变为com/mec/util
            int lastIndex = packName.lastIndexOf(".");
            int secandIndex = packName.substring(0, lastIndex).lastIndexOf(".");
            //设置包命为com.mec.util即为去掉.test.java
            packName = packName.substring(0, secandIndex);
            //将路径变为com/mec/util
            packageName = packName.substring(0, secandIndex).replace(".", "/");
        }

        try {
            //加载packageName的路径返回值为Enumeration<URL>类型
            Enumeration<URL> url = classLoader.getResources(packageName);
            //如果包名正确且不是空包，循环
            while(url.hasMoreElements()) {
                URL ur = url.nextElement();
                //查看url的类型
                String type = ur.getProtocol();
                //如果是jar包类型
                if(type.equals("jar")) {
                    //处理jarbao,方法在后面
                    dealJar(ur);
                    //如果是file类型
                } else if(type.equals("file")) {
                    //URL转换为file类型，File构造方法里有File(URI uri)
                    File file = new File(ur.toURI());
                    //如果file是个目录
                    if(file.isDirectory()) {
                        //调用处理目录文件
                        dealFiles(packName, file);
                    } else {
                        //处理文件
                        String name = file.getName();
                        //如果是class文件处理
                        if(name.contains(".class")) {
                            //调用处理java类型的文件也就是**.class，是我们想要的文件
                            deaJavaFile(file, packName);
                        } else {
                            //不是继续循环
                            continue;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return this;
    }

    //处理目录文件
    private void dealFiles(String packageName, File file) {
        //安全期间判断下文件是否存在，存在的话进行操作
        if(file.exists()) {
            //file一定是目录型文件所以得到该目录下所有文件遍历它们
            File[] files = file.listFiles();
            for(File childfile : files) {
                //如果子文件是目录，则递归处理，调用本方法递归。
                if(childfile.isDirectory()) {
                    //注意递归时候包名字要加上".文件名"后为新的包名
                    //因为后面反射时需要类名，也就是com.mec.***
                    dealFiles(packageName + "." + childfile.getName(), childfile);
                } else {
                    //如果该文件不是目录。
                    String name = childfile.getName();
                    //该文件是class类型
                    if(name.contains(".class")) {
                        deaJavaFile(childfile, packageName);
                    } else {
                        continue;
                    }
                }
            }
        } else {
            return;
        }
    }

    //得到了我们想要的**.class文件，处理它，我们希望返回该类型即可。
    private void deaJavaFile(File file, String packageName) {
        //以下操作得到类型
        int index = file.getName().lastIndexOf(".class");
        String filename = file.getName().substring(0, index);
        Class<?> klass = null;
        try {
            klass = Class.forName(packageName + "." + filename);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //得到类型后，用户怎么使用这个类型是用户的事情，和工具无关，所以提供一个抽象方法将klass传过去
        dealClass(klass);
    }

    //处理jar包类型
    private void dealJar(URL url) {
        //以下六行都是处理jar的固定方法
        JarURLConnection jarURLConnection;
        try {
            jarURLConnection = (JarURLConnection) url.openConnection();
            JarFile jarFile = jarURLConnection.getJarFile();
            Enumeration<JarEntry> jarEntries = jarFile.entries();
            while(jarEntries.hasMoreElements()) {
                JarEntry jar = jarEntries.nextElement();
                //如果是目录，或者不是**.class类型不处理
                if(jar.isDirectory() || !jar.getName().endsWith(".class")) {
                    continue;
                }
                //处理class类型
                String jarName = jar.getName();
                int dotIndex = jarName.indexOf(".class");
                String className = jarName.substring(0, dotIndex).replace("/", ".");
                Class<?> klass = Class.forName(className);
                //调用抽象方法,给使用者返回Class<?>类型
                dealClass(klass);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public PackageScanner scanClassPath(String absolute, String root) {
        File file = new File(absolute);
        if(file.exists()) {
            if(file.isFile()) {
                if(file.getName().endsWith(".java")){
                    int index = file.getName().lastIndexOf(".java");
                    String filename = file.getName().substring(0, index);
                    Class<?> klass = null;
                    try {
                        klass = Class.forName(root + "." + filename);
                        dealClass(klass);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return this;
    }
}
