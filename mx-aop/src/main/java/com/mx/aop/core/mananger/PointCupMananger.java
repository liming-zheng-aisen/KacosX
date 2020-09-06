package com.mx.aop.core.mananger;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc
 * @Author Zheng.LiMing
 * @Date 2020/4/1
 */
public class PointCupMananger {

    private static  Map<String,String> cache = new HashMap<>();

    private static final String DOUBLE_SPACE = "  ";

    private static final String SPACE = " ";

    /**
     * 注册一个切入点
     * @param k 方法名
     * @param v 表达式
     * @throws Exception
     */
    public static void regiest(String k , String v) throws Exception{
        if (cache.containsKey(k)){
            throw new Exception("切入点不允许出现重名，请重新更换方法名称！");
        }
        while (v.indexOf(DOUBLE_SPACE)>=0){
           v = v.replace(DOUBLE_SPACE,SPACE);
        }
        v = v.replace(".","\\.");
        v = v.replace("*",".*");
        v = v.replace("(","\\(");
        v = v.replace(")","\\)");
        cache.put(k,v);
    }

    /**
     * 获取切入点的表达式
     * @param k
     * @return
     */
    public static String get(String k){
        if (cache.containsKey(k)){
            return cache.get(k);
        }
        return null;
    }

}
