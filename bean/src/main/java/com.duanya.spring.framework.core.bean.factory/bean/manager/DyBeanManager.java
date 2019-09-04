package com.duanya.spring.framework.core.bean.factory.bean.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @Desc BeanManager
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyBeanManager {

    private static List<Class> classContainer=new ArrayList<>();;

    public static void registerClassByClassString(List<String> result){
        for (String item:result){
            try {
                classContainer.add(Class.forName(item));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        classContainer = new ArrayList<>(new HashSet<>(classContainer));
    }

    public static List<Class> getClassContainer() {
        return classContainer;
    }

    public static boolean isLoad(){
        return classContainer.size()>0;
    }

}
