package com.duanya.spring.common.scanner.api;

import java.util.List;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description
 */
public interface IDyScanner {

    public List<String> doScanner(boolean recursion)throws Exception;

}
