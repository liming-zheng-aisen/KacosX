package com.macos.framework.core.bean.manage;

import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.util.BeanDefinitionUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @Desc bean class全局管理
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class BeanManager {

    /**bean管理容器*/
    private static volatile Set<BeanDefinition> classContainer=new HashSet<>();;

    /**注册一个bean*/
    public static void registerClassBySet(Set<Class> result){
        if (result==null || result.size()==0){
            return;
        }
        Iterator iterator = result.iterator();
        while (iterator.hasNext()){
            Class c = (Class) iterator.next();
            BeanDefinition beanDefinition = BeanDefinitionUtil.convertToBeanDefinition(c);
            if (!isExist(beanDefinition)) {
                classContainer.add(beanDefinition);
            }
        }
    }

    /**获取bean管理容器*/
    public static Set<BeanDefinition> getClassContainer() {
        return classContainer;
    }

    /**判断容器是否已经加载*/
    public static boolean isLoad(){
        return classContainer.size()>0;
    }

    public static boolean isExist(BeanDefinition beanDefinition){
        for (BeanDefinition b : classContainer){
            if (b.getTarget()==beanDefinition.getTarget()){
                return true;
            }
        }
        return false;
    }
}
