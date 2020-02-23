package com.macos.framework.core.env;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aisen
 * @mail aisen.zheng.tech@linkkt.one
 * @creater 2020/1/10 14:25:53
 * @desc env环境
 */
public class ApplicationENV {

    private static ConcurrentHashMap<String,Object> env = new ConcurrentHashMap<>();

    public ApplicationENV addElement(String key,Object value){
        env.put(key,value);
        return this;
    }

    public ApplicationENV addElementByMap(Map map){
        Set<String> keySet = map.keySet();
        for (String key : keySet ){
            env.put(key,map.get(key));
        }
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

    public Object getElementValue(String key,Object defauleValue){
        if (env.containsKey(key)){
           return env.get(key);
        }
        return defauleValue;
    }
    private static volatile Set<String> keyCaches = new HashSet<>();

    public ApplicationENV updateElementsAndKeyCache(Properties properties){
        for (String key : properties.stringPropertyNames()) {
            if (env.get(key).equals(properties.getProperty(key))){
                keyCaches.add(key);
            }
            env.put(key , properties.getProperty(key));
        }
        return this;
    }

    public ApplicationENV removeCache(){
        keyCaches.clear();
        return this;
    }



}
