package com.macos.framework.core.handle;

import com.macos.framework.annotation.Configuration;
import com.macos.framework.annotation.MacosXScanner;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandle;
import com.macos.framework.core.util.AnnotationUtil;

import java.util.Set;

/**
 * @Desc @Configuration处理类
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
public class ConfigurationHandle implements BaseHandle {

    @Override
    public void doHandle(Class c) throws Exception {

        if (AnnotationUtil.hasAnnotion(c,Configuration.class)){
           registePathBeanDefinition(c);
        }

    }

    private void scannerHandle() throws Exception {
       Set<BeanDefinition> classContainer = BeanManager.getClassContainer();
       if (classContainer==null||classContainer.size()==0){
           return;
       }
       MaocsXScannerHandle handle = new MaocsXScannerHandle();
       for (BeanDefinition beanDefinition:classContainer){
           Class target= beanDefinition.getTarget();
           if (target.isAnnotationPresent(Configuration.class)&&target.isAnnotationPresent(MacosXScanner.class)){
              handle.doHandle(target);
           }
       }
    }

    private void registePathBeanDefinition(Class c) throws Exception {
           BeanManager beanManager =  new BeanManager();
           beanManager.registerBean(null,c);
    }

}
