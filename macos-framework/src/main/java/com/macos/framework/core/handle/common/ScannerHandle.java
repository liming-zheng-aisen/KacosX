package com.macos.framework.core.handle.common;

import com.macos.common.scanner.impl.ScannerImpl;
import com.macos.common.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @Desc 扫描处理
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
public class ScannerHandle {

    private final static ScannerImpl scanner=new ScannerImpl();

    public static Set<Class> doHandle(String[] basePath,Set<Class> result) throws Exception {

        if (basePath == null || basePath.length == 0){
            return result;
        }

        if (result==null){
            result = new HashSet<>();
        }

        for (String base : basePath){
           Set<Class> list = scanner.doScanner(base);
           result.addAll(list);
        }

        return result;
    }

    public static Set<Class> doHandle(String basePath,Set<Class> result) throws Exception {

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
