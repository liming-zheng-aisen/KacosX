package com.macos.framework.core.load.conf;

import com.macos.common.properties.LoadPropeties;
import com.macos.common.properties.PropertiesException;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.AutoConfiguration;
import com.macos.framework.annotation.MacosApplication;
import com.macos.framework.core.load.abs.BeanLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description 加载propert配置文件
 */
public class ConfigurationLoader extends BeanLoad {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationLoader.class);

    /**
     * evn全局配置文件。
     */
    private static Properties evn=new Properties();

    /**
     * 默认的配置文件名
     */
    private final static String DEFAULT_PROPERTIES_NAME="application.properties";

    public ConfigurationLoader(){

    }

    public ConfigurationLoader(BeanLoad beanLoad){
        nextLoader=beanLoad;
    }
    /**
     * 开始加载配置
     * @param c
     */
    @Override
    public void load(Class c) throws Exception {

       if (hasAnnotation(c)){
        //加载配置文件
        loadProperties(c,null);
       }

        if (null!=nextLoader){
            nextLoader.load(c);
        }

    }

    private boolean hasAnnotation(Class c){
        if (c.isAnnotationPresent(MacosApplication.class)||c.isAnnotationPresent(AutoConfiguration.class)){
            return true;
        }else {
            Annotation[] annotations=c.getAnnotations();
            boolean result=false;
            for (Annotation a:annotations){
               result = findAnnotation(a);
               if (true==result){
                   return result;
               }
            }
            return result;
        }
    }

    private  boolean findAnnotation(Annotation annotation){

        if (annotation.annotationType().isAnnotationPresent(MacosApplication.class)||annotation.annotationType().isAnnotationPresent(AutoConfiguration.class)){
            return true;
        }

        Annotation[] ans=annotation.getClass().getAnnotations();
        boolean result=false;

        for (Annotation a:ans){

           result= findAnnotation(a);

           if (true==result){
               return result;
           }
        }

        return result;

    }

    /**
     * 加载配置文件
     * @param c
     * @param propertiesName
     */
    public static void loadProperties(Class c,String propertiesName){

        try {
            if(StringUtils.isEmptyPlus(propertiesName)){
                propertiesName=DEFAULT_PROPERTIES_NAME;
            }
            LoadPropeties.doLoadProperties(evn,propertiesName,c);
            //如果存在其他配置文件，读取文件流并加载到evn
            String otherProperties=evn.getProperty("macos.conf.loader.other");
            if (StringUtils.isNotEmptyPlus(otherProperties)){
                String [] names=otherProperties.split(",");
                for (String n : names){
                    LoadPropeties.doLoadProperties(evn,n,c);
                }
            }
            log.info("配置文件{}加载成功",DEFAULT_PROPERTIES_NAME);
        } catch (PropertiesException e) {
            log.warn("没有加载到配置文件{}",DEFAULT_PROPERTIES_NAME);
            e.printStackTrace();
        }

    }

    /**
     * 获取全局配置类
     * @return
     */
    public static Properties getEvn(){
        return evn;
    }

    public static void setEvn(Properties evn) {
        ConfigurationLoader.evn = evn;
    }
}
