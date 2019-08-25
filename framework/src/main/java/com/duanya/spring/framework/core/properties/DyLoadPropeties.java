package com.duanya.spring.framework.core.properties;

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
public class DyLoadPropeties {

    public static Properties doLoadProperties( Properties properties,String contextConfigLocation,Class c) throws DyPropertiesException {

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
            if (contextConfigLocation.endsWith(DyPropertiesType.PROPERTIES_SUFFIC)) {
                properties.load(br);
            }else if (contextConfigLocation.endsWith(DyPropertiesType.XML_SUFFIC)){
                properties.loadFromXML(inputStream);
            }else{
                throw new  DyPropertiesException("不支持该类型的配置文件，目前只支持"+DyPropertiesType.PROPERTIES_SUFFIC+"、"+DyPropertiesType.XML_SUFFIC+"后缀的配置文件");
            }
            return properties;
        } catch (IOException e) {
            throw new  DyPropertiesException("配置文件加载异常,请检查路径！",e);
        }finally {

            if (null!=inputStream){
                try {
                    br.close();
                    inputStream.close();
                } catch (IOException e) {
                    throw new  DyPropertiesException("配置文件关闭流异常",e);
                }
            }

        }

    }

}
