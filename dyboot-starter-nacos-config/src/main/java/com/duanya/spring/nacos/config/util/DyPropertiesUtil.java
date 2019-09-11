package com.duanya.spring.nacos.config.util;

import java.util.Properties;

/**
 * @Desc DyPropertiesUtil
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class DyPropertiesUtil {

    public static void cloneEvn(Properties t,Properties s){
        if (t==null||s==null){
            return;
        }
        t.keySet().stream().forEach(key-> s.setProperty((String)key,t.getProperty((String)key)));
    }
}
