package com.mx.common.scanner.api;

import java.util.Set;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description
 */
public interface ScannerApi {

    public Set<Class> doScanner(String packageName)throws Exception;

    public Set<Class> doScanner(String packageName, Class<?> ...annotations)throws Exception;

}
