package com.macos.common.util;

import java.lang.annotation.Annotation;

/**
 * @author Aisen
 * @mail aisen.zheng.tech@linkkt.one
 * @creater 2020/1/2 15:05:43
 * @desc
 */
public class AnnotationUtil {

    public static Annotation findAnnotation(Class target,Class annotation){
        if (target.isAnnotationPresent(annotation)){
            return target.getAnnotation(annotation);
        }
        return null;
    }

    public static boolean hasAnnotion(Class target,Class... annotation){
        for (Class c : annotation){
            boolean result = target.isAnnotationPresent(c);
            if (result){
                return result;
            }
        }
        return false;
    }

}
