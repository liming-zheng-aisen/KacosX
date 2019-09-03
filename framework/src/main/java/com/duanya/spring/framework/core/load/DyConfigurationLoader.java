package com.duanya.spring.framework.core.load;

import com.duanya.spring.commont.util.StringUtils;
import com.duanya.spring.framework.context.exception.DyContextException;
import com.duanya.spring.framework.core.properties.DyLoadPropeties;
import com.duanya.spring.framework.core.properties.DyPropertiesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/19
 * @description 加载propert配置文件
 */
public class DyConfigurationLoader implements DyBeanLoad {

    private static final Logger log = LoggerFactory.getLogger(DyConfigurationLoader.class);

    /**
     * evn全局配置文件。
     */
    private static Properties evn=new Properties();

    /**
     * 默认的配置文件名
     */
    private final static String DEFAULT_PROPERTIES_NAME="dy-application.properties";


    /**
     * 开始加载配置
     * @param c
     */
    @Override
    public void load(Class c) throws ClassNotFoundException, InvocationTargetException, InstantiationException, DyContextException, IllegalAccessException {
        //加载配置文件
        loadProperties(c,null);
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
            DyLoadPropeties.doLoadProperties(evn,propertiesName,c);
            //如果存在其他配置文件，读取文件流并加载到evn
            String otherProperties=evn.getProperty("dy.properties.loader.other");
            if (StringUtils.isNotEmptyPlus(otherProperties)){
                String [] names=otherProperties.split(",");
                for (String n : names){
                    DyLoadPropeties.doLoadProperties(evn,n,c);
                }
            }
            log.info("配置{}文件加载成功",DEFAULT_PROPERTIES_NAME);
        } catch (DyPropertiesException e) {
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


}
