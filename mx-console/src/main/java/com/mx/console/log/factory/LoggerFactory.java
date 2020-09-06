package com.mx.console.log.factory;

import com.mx.console.log.Logger;
import com.mx.console.log.impl.MxLogger;

/**
 * @Desc 日志工厂类
 * @Author Zheng.LiMing
 * @Date 2020/8/20
 */
public class LoggerFactory {

    public static Logger<?> buildLogger(Class c){
        return new MxLogger<>(c.getName());
    }

    public static Logger<?> buildLogger(String clazz){
        return new MxLogger<>(clazz);
    }

}
