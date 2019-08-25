package com.duanya.start.web.run;

import com.duanya.spring.framework.context.exception.DyContextException;
import com.duanya.spring.framework.core.annotation.DyBootApplication;
import com.duanya.spring.framework.core.annotation.DyScanner;
import com.duanya.spring.framework.core.load.DyClassLoader;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import com.duanya.spring.framework.core.load.DyIocLoader;
import com.duanya.start.web.times.Timer;
import com.duanya.start.web.tomcat.InsideTomcat;
import org.apache.catalina.LifecycleException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 启动
 */
public class DyBootApplicationWeb {


    public static void run(Class main) {
        try {

            authorization(main);
            //计时器
            Timer timer=new Timer();
            timer.doStart();
            //打印logo
            printLogo(main);
            //加载配置文件
            DyConfigurationLoader.load(main,null);
            //扫描加载类
            DyClassLoader classLoader=new DyClassLoader();
            if (main.isAnnotationPresent(DyScanner.class)){
                DyScanner scanner= (DyScanner) main.getAnnotation(DyScanner.class);
                String[] packageNames=scanner.packageNames();
                if (packageNames.length==0){
                    System.out.println("扫描路径不允许为空或\"  \"");
                    close();
                }
                for (String p:packageNames){
                    classLoader.load(p);
                }
            }else {
                classLoader.load(main);
            }
            //spring上下文加载
            DyIocLoader.load();
            //最后启动tomcat
            InsideTomcat.start(timer,main);

        } catch (LifecycleException e) {
            e.printStackTrace();
            close();
        } catch (InstantiationException e) {
            e.printStackTrace();
            close();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            close();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            close();
        } catch (DyContextException e) {
            e.printStackTrace();
            close();
        }
    }
    private static void close(){
        System.exit(0);
    }

    private static void printLogo(Class c){
        try {
            File file = new File(c.getResource("/dy.txt").getPath());
            if (file.exists()) {
                FileReader  in = new FileReader(file);
                BufferedReader buff = new BufferedReader(in);
                while (buff.ready()){
                    System.out.println(buff.readLine());
                }
                buff.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void authorization(Class c){
        if (!c.isAnnotationPresent(DyBootApplication.class)){
            System.out.println("未找到加载类，请配置DyBootApplication！");
            close();
        }
    }
}
