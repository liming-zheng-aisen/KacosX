package com.macos.common.scanner.impl;

import com.macos.common.scanner.api.ScannerApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description 扫描
 */
public class ScannerImpl implements ScannerApi {

    private static final Logger log = LoggerFactory.getLogger(ScannerImpl.class);

    @Override
    public Set<Class> doScanner(String packageName) throws Exception {
        Set<Class> result=new HashSet<>();
        ScanStarter scanPackage = new ScanStarter();
        scanPackage.addPackage(packageName);
        scanPackage.setFilter(new ScanStarter.ScanPackageFilter()
        {
            @Override
            public boolean accept(Class<?> clazz)
            {
                return true;
            }
        });
        scanPackage.setListener(new ScanStarter.ScanPackageListener()
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
        ScanStarter scanPackage = new ScanStarter();
        scanPackage.addPackage(packageName);
        scanPackage.setFilter(new ScanStarter.ScanPackageFilter()
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
        scanPackage.setListener(new ScanStarter.ScanPackageListener()
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
