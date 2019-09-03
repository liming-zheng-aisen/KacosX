package com.duanya.start.web.run;

import com.duanya.spring.framework.context.exception.DyContextException;
import com.duanya.spring.framework.core.annotation.DyAutoConfiguration;
import com.duanya.spring.framework.core.annotation.DyBootApplication;
import com.duanya.spring.framework.core.annotation.DyScanner;
import com.duanya.spring.framework.core.load.DyClassLoader;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import com.duanya.spring.framework.core.load.DyIocLoader;
import com.duanya.spring.framework.jdbc.datasource.DyDataSourceFactory;
import com.duanya.start.web.times.Timer;
import com.duanya.start.web.tomcat.InsideTomcat;
import org.apache.catalina.LifecycleException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 启动
 */
public class DyBootApplicationWeb {

    static Logger  logger=Logger.getLogger(DyBootApplicationWeb.class.getName());

    public static void run(Class main) {
        try {
            //计时器
            Timer timer=new Timer();
            timer.doStart();
            //打印logo
            printLogo(main);
            //加载配置文件
            new DyConfigurationLoader().load(main);
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
                    try {
                        classLoader.load(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                classLoader.load(main);
            }

            //spring上下文加载
            new DyIocLoader().load(main);

            //加载jdbc驱动
            DyDataSourceFactory.createDataSource(DyConfigurationLoader.getEvn());

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
        if (c.isAnnotationPresent(DyBootApplication.class)||(c.isAnnotationPresent(DyAutoConfiguration.class)&&c.isAnnotationPresent(DyScanner.class))){
            logger.log(Level.INFO,"DY BOOT 认证通过，正在启动......");
        }else {
            logger.log(Level.WARNING,"未找到加载类，请配置DyBootApplication！");
            close();
        }
    }
}
