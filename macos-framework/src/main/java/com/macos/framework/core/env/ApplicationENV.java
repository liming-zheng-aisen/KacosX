package com.macos.framework.core.env;

import java.util.*;

/**
 * @author Aisen
 * @mail aisen.zheng.tech@linkkt.one
 * @creater 2020/1/10 14:25:53
 * @desc
 */
public class ApplicationENV {
    private static Map<String,Object> env = new HashMap<>();

    public ApplicationENV addElement(String key,Object value){
        env.put(key,value);
        return this;
    }

    public ApplicationENV removeAll(){
        env.clear();
        return this;
    }

    public ApplicationENV removeElement(String key){
        env.remove(key);
        return this;
    }

    public ApplicationENV addElementsByPropreties(Properties properties){
        for (String key : properties.stringPropertyNames()) {
            env.put(key , properties.getProperty(key));
        }
        return this;
    }

    public Object getElementValue(String key){
        if (env.containsKey(key)){
            return env.get(key);
        }
        return null;
    }



}
