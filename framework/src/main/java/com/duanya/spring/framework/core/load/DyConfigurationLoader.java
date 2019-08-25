package com.duanya.spring.framework.core.load;

import com.duanya.spring.commont.util.StringUtils;
import com.duanya.spring.framework.context.exception.DyContextException;
import com.duanya.spring.framework.core.properties.DyLoadPropeties;
import com.duanya.spring.framework.core.properties.DyPropertiesException;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description 加载propert配置文件
 */
public final class DyConfigurationLoader {

    /**
     * evn全局配置文件。
     */
    private static Properties evn=new Properties();
    /**
     * 是否加载过（保证安全，只能调用一次）
     */
    private static boolean isRun=false;

    /**
     * 默认的配置文件名
     */
    private final static String DEFAULT_PROPERTIES_NAME="dy-application.properties";

    private DyConfigurationLoader(){

    }

    /**
     * 开始加载配置
     * @param c
     * @param propertiesName
     */
    public synchronized static void load(Class c,String propertiesName) throws ClassNotFoundException, InvocationTargetException, InstantiationException, DyContextException, IllegalAccessException {
        //加载过一次就不再加载
        if (isRun==true){
            return;
        }
        //加载配置文件
        loadProperties(c,propertiesName);
        isRun=true;
    }

    /**
     * 加载配置文件
     * @param c
     * @param propertiesName
     */
    private static void loadProperties(Class c,String propertiesName){

        try {
            if(StringUtils.isEmptyPlus(propertiesName)){
                propertiesName=DEFAULT_PROPERTIES_NAME;
            }
            DyLoadPropeties.doLoadProperties(evn,propertiesName,c);
            //如果存在其他配置文件，读取文件流并加载到evn
            String otherProperties=evn.getProperty("dy.properties.loader.other");
            if (StringUtils.isNotEmptyPlus(otherProperties)){
                String [] names=otherProperties.split(",");
                for (String n : names){
                    DyLoadPropeties.doLoadProperties(evn,n,c);
                }
            }
        } catch (DyPropertiesException e) {
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


}
