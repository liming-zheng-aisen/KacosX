package com.macos.common.util;

import com.macos.common.scanner.impl.ScannerImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * @Desc 扫描处理
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
public class ScannerUtil {

    private final static ScannerImpl scanner=new ScannerImpl();

    /**
     * 多个根路径扫描
     * @param basePath
     * @param result
     * @return
     * @throws Exception
     */
    public static Set<Class> scanner(String[] basePath,Set<Class> result) throws Exception {
        if (basePath == null || basePath.length == 0){
            return result;
        }
        for (String base : basePath){
           scanner(base,result);
        }
        return result;
    }

    /**
     * 根路径扫描
     * @param basePath
     * @param result
     * @return
     * @throws Exception
     */
    public static Set<Class> scanner(String basePath,Set<Class> result) throws Exception {

        if (StringUtils.isEmptyPlus(basePath)){
            return result;
        }

        if (result==null){
            result = new HashSet<>();
        }

        Set<Class> list = scanner.doScanner(basePath);
        result.addAll(list);

        return result;
    }
}
