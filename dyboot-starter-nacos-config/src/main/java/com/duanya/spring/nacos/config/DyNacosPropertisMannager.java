package com.duanya.spring.nacos.config;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Desc nacos配置管理器
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class DyNacosPropertisMannager {

    private Map<String,String> nacosProperties;

    private DyNacosPropertisMannager(){
        nacosProperties=new HashMap<>();
    }

    public void updateInputStream(String dataId,String content) throws UnsupportedEncodingException {

        if (nacosProperties.containsKey(dataId)){
            nacosProperties.remove(dataId);
        }

        nacosProperties.put(dataId,new String(content.getBytes(),"utf-8"));

    }

    public Properties getNacosEvn() throws IOException {

        StringBuffer buffer=new StringBuffer();

        nacosProperties.values().forEach(content->buffer.append(content));

        InputStream inputStream=new ByteArrayInputStream(buffer.toString().getBytes());

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

        Properties evn=new Properties();
        evn.load(br);

        return evn;

    }

    public static class Buider{
        private final static DyNacosPropertisMannager nacosPropertisMannager=new DyNacosPropertisMannager();
        public static DyNacosPropertisMannager  getMannager(){
            return nacosPropertisMannager;
        }
    }

}
