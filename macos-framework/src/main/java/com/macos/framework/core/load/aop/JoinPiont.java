package com.macos.framework.core.load.aop;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Desc JoinPiont
 * @Author Zheng.LiMing
 * @Date 2020/1/4
 */
@Data
public class JoinPiont {

    private Object target;

    private Object[] args;

    private Method method;

    public Object proceed(Object...param) throws InvocationTargetException, IllegalAccessException {
       return method.invoke(target,param);
    }


}
