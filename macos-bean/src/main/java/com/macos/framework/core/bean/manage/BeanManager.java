package com.macos.framework.core.bean.manage;

import com.macos.common.util.StringUtils;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.factory.api.BeanFactory;
import com.macos.framework.core.bean.util.BeanDefinitionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @Desc bean class全局管理
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
@Slf4j
public class BeanManager implements BeanFactory {

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
            if (!isExist(null,beanDefinition)) {
                classContainer.add(beanDefinition);
            }
        }
    }

    /**获取bean管理容器*/
    public static Set<BeanDefinition> getClassContainer() {
        return classContainer;
    }

    /**判断容器是否已经加载*/
    public  static boolean isLoad(){
        return classContainer.size()>0;
    }

    public static boolean isExist(String beanName,BeanDefinition beanDefinition){
        for (BeanDefinition b : classContainer){
            if (b.getTarget()==beanDefinition.getTarget()){
                if (StringUtils.isNotEmptyPlus(beanName) )
                {
                    if (beanName.equals(b.getBeanName())){
                        return true;
                    }
                   continue;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 获取bean定义
     * @param beanName
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static BeanDefinition getBeanDefinition(String beanName, Class beanClass) throws Exception {
        List<BeanDefinition> result = new ArrayList<>();
        for (BeanDefinition b : classContainer){
            if (StringUtils.isNotEmptyPlus(beanName) && b.getBeanName().equals(beanName) && hasClass(b,beanClass)){
                    result.add(b);
                    continue;
            }
           if (hasClass(b,beanClass)){
               result.add(b);
           }
        }

        if (result.size()==0){
            throw new Exception("未定义"+beanClass.toString());
        }
        if (result.size()>1){
            throw new Exception("多个"+beanClass.toString()+"的定义，请确保beanName唯一！");
        }
        return result.get(0);
    }

    /**
     * 是否包含该class
     * @param beanDefinition
     * @param beanClass
     * @return
     */
    public static boolean hasClass(BeanDefinition beanDefinition,Class beanClass){
        for (Class c : beanDefinition.getSuperClasses()){
            if (c.equals(beanClass)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean registerBean(String beanName,Class beanClass) {
        BeanDefinition beanDefinition = BeanDefinitionUtil.convertToBeanDefinition(beanClass);
        if (!isExist(beanName,beanDefinition)) {
            classContainer.add(beanDefinition);
            return true;
        }
        return false;
    }

    @Override
    public  Object getBean(String beanName,Class target) throws Exception {
        BeanDefinition beanDefinition = getBeanDefinition(beanName,target);
        ApplicationContextApi contextApi = beanDefinition.getContextApi();
        if (contextApi==null){
            return null;
        }
         return  contextApi.getBean(beanName,target);
    }
}
