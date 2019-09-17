package com.duanya.spring.framework.druid;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc DyServletConfig
 * @Author Zheng.LiMing
 * @Date 2019/9/14
 */
public class DyServletConfig implements ServletConfig {

    private ServletConfig config;

    private Map<String,String> map=new HashMap<>();


    public void put(String key,String value){
        map.put(key,value);
    }


    public  DyServletConfig(ServletConfig config){

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
