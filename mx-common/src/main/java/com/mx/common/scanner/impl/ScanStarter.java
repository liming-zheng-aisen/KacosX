package com.mx.common.scanner.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Desc DyScanStarter
 * @Author Zheng.LiMing
 * @Date 2019/9/9
 */
public class ScanStarter {
    /** 是否循环迭代 **/
    boolean recursive = true;
    /** 包名集合 **/
    private List<String> packageNames = new ArrayList<String>();
    /** 过滤器 **/
    private ScanPackageFilter filter = null;
    /** 监听器 **/
    private ScanPackageListener listener = null;

    /**
     * 是否循环迭代
     *
     * @return
     */
    public boolean isRecursive()
    {
        return recursive;
    }

    /**
     * 设置是否循环迭代
     *
     * @param recursive
     */
    public void setRecursive(boolean recursive)
    {
        this.recursive = recursive;
    }

    /**
     * 获得过滤器
     *
     * @return
     */
    public ScanPackageFilter getFilter()
    {
        return filter;
    }

    /**
     * 设置过滤器
     *
     * @param filter
     */
    public void setFilter(ScanPackageFilter filter)
    {
        this.filter = filter;
    }

    /**
     * 获得监听器
     *
     * @return
     */
    public ScanPackageListener getListener()
    {
        return listener;
    }

    /**
     * 设置监听器
     *
     * @param listener
     */
    public void setListener(ScanPackageListener listener)
    {
        this.listener = listener;
    }

    /**
     * 添加扫描包
     *
     * @param packageName
     */
    public void addPackage(String packageName)
    {
        if (packageName == null || !packageName.matches("[\\w]+(\\.[\\w]+)*"))
        {
            throw new IllegalArgumentException("非法包名.");
        }
        this.packageNames.add(packageName);
    }

    /**
     * 清空扫描包
     */
    public void clearPackage()
    {
        this.packageNames.clear();
    }

    /**
     * 扫描
     */
    public void scan()
    {
        for (String packageName : packageNames)
        {
            scan(packageName);
        }
    }

    /**
     * 是否接受
     *
     * @param clazz
     * @return
     */
    private boolean accept(Class<?> clazz)
    {
        if (this.filter != null)
        {
            return this.filter.accept(clazz);
        }
        return true;
    }

    /**
     * 触发扫描到合法类
     *
     * @param clazz
     * @return
     */
    private void trrigerOnScanClass(Class<?> clazz)
    {
        if (this.listener != null)
        {
            this.listener.onScanClass(clazz);
        }
    }

    /**
     * 扫描到类
     *
     * @param clazz
     * @return
     */
    private void onScanClass(Class<?> clazz)
    {
        if (accept(clazz))
        {
            trrigerOnScanClass(clazz);
        }
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param packageName
     * @return
     */
    private void scan(String packageName)
    {
        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs = null;
        try
        {
            dirs = Thread.currentThread().getContextClassLoader()
                    .getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements())
            {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol))
                {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath);
                }
                else if ("jar".equals(protocol))
                {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    JarFile jar = null;
                    try
                    {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements())
                        {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/')
                            {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName))
                            {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1)
                                {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/',
                                            '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive)
                                {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory())
                                    {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(
                                                packageName.length() + 1,
                                                name.length() - 6);
                                        try
                                        {
                                            // 添加到classes
                                            // 使用Class.forName会触发类静态方法
                                            Class<?> clazz = Thread.currentThread()
                                                    .getContextClassLoader().loadClass(
                                                            packageName + '.' + className);
                                            onScanClass(clazz);
                                        }
                                        catch (ClassNotFoundException e)
                                        {
                                            System.err.println("加载类出错");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch (IOException e)
                    {
                        System.err.println("在扫描用户定义视图时从jar包获取文件出错");
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("扫描出错");
            e.printStackTrace();
        }
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     */
    private void findAndAddClassesInPackageByFile(String packageName,
                                                  String packagePath)
    {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory())
        {
            System.err.println("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter()
        {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            @Override
            public boolean accept(File file)
            {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles)
        {
            // 如果是目录 则继续扫描
            if (file.isDirectory())
            {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath());
            }
            else
            {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try
                {
                    Class<?> clazz = Thread.currentThread().getContextClassLoader()
                            .loadClass(packageName + '.' + className);
                    onScanClass(clazz);
                }
                catch (ClassNotFoundException e)
                {
                    System.err.println("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 扫描包过滤器
     *
     * @author jianggujin
     *
     */
    public static interface ScanPackageFilter
    {
        /**
         * 是否接受
         *
         * @param clazz
         * @return
         */
        public boolean accept(Class<?> clazz);
    }

    /**
     * 扫描包监听
     *
     * @author jianggujin
     *
     */
    public static interface ScanPackageListener
    {
        /**
         * 扫描到合法类执行
         *
         * @param clazz
         */
        public void onScanClass(Class<?> clazz);
    }


}
