package com.macos.aop.core.mananger;


import com.macos.aop.core.enums.AopGroupEnum;
import com.macos.aop.core.even.bean.aop.AopEven;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc 切面事件管理器
 * @Author Zheng.LiMing
 * @Date 2020/1/15
 */
public class AopEvenMananger {

    private static volatile ConcurrentHashMap<AopGroupEnum,Set<AopEven>> evens = new ConcurrentHashMap<>();

    /**
     *
     * @param evenGroup
     * @param even
     */
    public static void registerEven(AopGroupEnum evenGroup,AopEven even){
       Set<AopEven> evenSet = evens.get(evenGroup);
       if (evenSet==null){
           evenSet = new HashSet<>();
           evens.put(evenGroup,evenSet);
       }
       evenSet.add(even);
    }

    /**
     * 获取切面事件集
     * @param evenGroup
     * @param matching 当前对象全路径名
     * @return
     */
    public static Set<AopEven> findEvens(AopGroupEnum evenGroup, String matching){
        if (evens.size()==0 || evens.get(evenGroup)==null){
            return null;
        }
        Set<AopEven> evenSet = new HashSet<>();
        Set<AopEven> aopEvens = evens.get(evenGroup);
        for (AopEven aopEven:aopEvens){
           if (matching.matches(aopEven.getMatching())){
               evenSet.add(aopEven);
           }
        }
        return evenSet;
    }
}
