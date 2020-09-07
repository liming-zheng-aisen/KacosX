package com.mx.common.properties;

import com.mx.console.log.Logger;
import com.mx.console.log.factory.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 加载配置
 */
public class PropertiesUtil {

    private final static String CLASSPATH="classpath";

    private final static String CONFIG = "config";

    private final static Logger log = LoggerFactory.buildLogger(PropertiesUtil.class);

    public static Properties loadProperties(String source,Class c) throws PropertiesException, IOException {
        if (!source.endsWith(PropertiesType.PROPERTIES_SUFFIC)){
            return null;
        }

        Properties  properties=new Properties();

        BufferedReader br =null;
        File file = null;
        FileInputStream fileInputStream = null;
        InputStream inputStream=null;
        String[] values = source.split("\\:");

        try {
            if (values.length > 1) {
                if (CLASSPATH.equals(values[0].toLowerCase())) {
                    inputStream = c.getClassLoader().getResourceAsStream(values[1]);
                    br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    properties.load(br);
                } else {
                    file = new File(source);
                    fileInputStream = new FileInputStream(file);
                    properties.load(fileInputStream);
                }
            } else {
                try {
                    inputStream = c.getClassLoader().getResourceAsStream(values[0]);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
                if (inputStream == null) {
                    file = new File(System.getProperty("user.dir") + "/" + CONFIG + "/" + source);
                    fileInputStream = new FileInputStream(file);
                    properties.load(fileInputStream);
                } else {
                    br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    properties.load(br);
                }
            }

        }finally{
            if (br!=null){
                br.close();
            }
            if (inputStream!=null){
                inputStream.close();
            }
            if (fileInputStream!=null){
                fileInputStream.close();
            }
        }

        return properties;

    }

    public static Map loadYaml(String source,Class c) throws IOException {
        if (!(source.endsWith(PropertiesType.YML_SUFFIC) || source.endsWith(PropertiesType.YAML_SUFFIC))){
            return null;
        }
        //实例化解析器
        Yaml yaml = new Yaml();
        BufferedReader br =null;
        File file = null;
        FileInputStream fileInputStream = null;
        InputStream inputStream=null;
        String[] values = source.split("\\:");

        try {
            if (values.length > 1) {
                if (CLASSPATH.equals(values[0].toLowerCase())) {
                    inputStream = c.getClassLoader().getResourceAsStream(values[1]);
                    br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    return yaml.loadAs(br, Map.class);
                } else {
                    file = new File(source);
                    fileInputStream = new FileInputStream(file);
                    return yaml.loadAs(fileInputStream, Map.class);
                }
            } else {
                try {
                    inputStream = c.getClassLoader().getResourceAsStream(values[0]);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
                if (inputStream == null) {
                    file = new File(System.getProperty("user.dir") + "/" + CONFIG + "/" + source);
                    fileInputStream = new FileInputStream(file);
                    return yaml.loadAs(fileInputStream, Map.class);
                } else {
                    br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    return yaml.loadAs(br, Map.class);
                }
            }

        }finally{
            if (br!=null){
                br.close();
            }
            if (inputStream!=null){
                inputStream.close();
            }
            if (fileInputStream!=null){
                fileInputStream.close();
            }
        }

    }

}

