package com.macos.common.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 加载配置
 */
public class LoadPropeties {

    private static final Logger log = LoggerFactory.getLogger(LoadPropeties.class);

    public static Properties doLoadProperties( Properties properties,String contextConfigLocation,Class c) throws PropertiesException {

        if (null ==properties){
            properties=new Properties();
        }

        InputStream inputStream=null;

        BufferedReader br=null;
        try {

            inputStream= c.getClassLoader().getResourceAsStream(contextConfigLocation);
            if (null==inputStream){
                return properties;
            }
             br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            if (contextConfigLocation.endsWith(PropertiesType.PROPERTIES_SUFFIC)) {
                properties.load(br);
            }else if (contextConfigLocation.endsWith(PropertiesType.XML_SUFFIC)){
                properties.loadFromXML(inputStream);
            }else{
                log.error("不支持该类型的配置文件");
                throw new PropertiesException("不支持该类型的配置文件，目前只支持"+ PropertiesType.PROPERTIES_SUFFIC+"、"+ PropertiesType.XML_SUFFIC+"后缀的配置文件");
            }
            return properties;
        } catch (IOException e) {
            log.warn("配置文件加载异常,请检查路径！");
            throw new PropertiesException("配置文件加载异常,请检查路径！",e);
        }finally {

            if (null!=inputStream){
                try {
                    br.close();
                    inputStream.close();
                } catch (IOException e) {
                    log.warn("配置文件关闭流异常");
                    throw new PropertiesException("配置文件关闭流异常",e);
                }
            }

        }

    }

}
