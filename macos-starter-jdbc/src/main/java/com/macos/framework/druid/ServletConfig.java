package com.macos.framework.druid;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc DyServletConfig
 * @Author Zheng.LiMing
 * @Date 2019/9/14
 */
public class ServletConfig implements javax.servlet.ServletConfig {

    private javax.servlet.ServletConfig config;

    private Map<String,String> map=new HashMap<>();


    public void put(String key,String value){
        map.put(key,value);
    }


    public ServletConfig(javax.servlet.ServletConfig config){

    }

    @Override
    public String getServletName() {
        return config.getServletName();
    }

    @Override
    public ServletContext getServletContext() {
        return config.getServletContext();
    }

    @Override
    public String getInitParameter(String s) {
        if (map.containsKey(s)){
            return map.get(s);
        }
        return config.getInitParameter(s);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return config.getInitParameterNames();
    }
}
