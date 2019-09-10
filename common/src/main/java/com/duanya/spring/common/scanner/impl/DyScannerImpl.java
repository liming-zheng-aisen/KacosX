package com.duanya.spring.common.scanner.impl;

import com.duanya.spring.common.scanner.api.IDyScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 扫描
 */
public class DyScannerImpl implements IDyScanner {

    private static final Logger log = LoggerFactory.getLogger(DyScannerImpl.class);

    @Override
    public Set<Class> doScanner(String packageName) throws Exception {
        Set<Class> result=new HashSet<>();
        DyScanStarter scanPackage = new DyScanStarter();
        scanPackage.addPackage(packageName);
        scanPackage.setFilter(new DyScanStarter.ScanPackageFilter()
        {
            @Override
            public boolean accept(Class<?> clazz)
            {
                return true;
            }
        });
        scanPackage.setListener(new DyScanStarter.ScanPackageListener()
        {
            @Override
            public void onScanClass(Class<?> clazz)
            {
                result.add(clazz);
            }
        });
        scanPackage.scan();
        return result;
    }

    @Override
    public Set<Class> doScanner(String packageName, Class<?> ...annotations) throws Exception {
        Set<Class> result=new HashSet<>();
        DyScanStarter scanPackage = new DyScanStarter();
        scanPackage.addPackage(packageName);
        scanPackage.setFilter(new DyScanStarter.ScanPackageFilter()
        {
            @Override
            public boolean accept(Class<?> clazz)
            {
                for (Class a:annotations){
                    if (clazz.isAnnotationPresent(a)){
                        return true;
                    }
                }
                return false;
            }
        });
        scanPackage.setListener(new DyScanStarter.ScanPackageListener()
        {
            @Override
            public void onScanClass(Class<?> clazz)
            {
               result.add(clazz);
            }
        });
        scanPackage.scan();
        return result;
    }
}
