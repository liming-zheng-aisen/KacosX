package com.macos.framework.core.bean.manage;

import com.macos.common.util.AnnotationUtil;
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

    /**
     * bean管理容器
     */
    private static  Set<BeanDefinition> classContainer = new HashSet<>();

    /**
     * 注册bean
     */
    public static void registerClassBySet(Set<Class> result) {
        if (result == null || result.size() == 0) {
            return;
        }
        Iterator iterator = result.iterator();
        while (iterator.hasNext()) {
            Class c = (Class) iterator.next();
            registerClass(c);
        }
    }

    /**
     * 注册一个bean
     */
    public static void registerClass(Class result) {
        if (result == null) {
            return;
        }
        BeanDefinition beanDefinition = BeanDefinitionUtil.convertToBeanDefinition(result);
        if (!isExist(null, beanDefinition)) {
            classContainer.add(beanDefinition);
        }
    }

    /**
     * 获取bean管理容器
     */
    public static Set<BeanDefinition> getClassContainer() {
        return classContainer;
    }

    /**
     * 判断容器是否已经加载
     */
    public static boolean isLoad() {
        return classContainer.size() > 0;
    }

    public static boolean isExist(String beanName, BeanDefinition beanDefinition) {
        for (BeanDefinition b : classContainer) {
            if (b.getTarget() == beanDefinition.getTarget()) {
                if (StringUtils.isNotEmptyPlus(beanName)) {
                    if (beanName.equals(b.getBeanName())) {
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
     *
     * @param beanName bean的名称
     * @param beanClass class对象
     * @param exclude 是否排除接口
     * @return
     * @throws Exception
     */
    public static BeanDefinition getBeanDefinition(String beanName, Class beanClass,boolean exclude) throws Exception {
        List<BeanDefinition> result = new ArrayList<>();

        //优先通过beanName获取
        for (BeanDefinition b : classContainer) {
            if (StringUtils.isNotEmptyPlus(beanName)) {
                if (b.getBeanName().equals(beanName) && hasClass(b, beanClass)) {
                    if (exclude && b.getTarget().isInterface()){
                        continue;
                    }
                    result.add(b);
                }
                continue;
            }
        }

        if (result.size() == 0) {
            for (BeanDefinition b : classContainer) {
                if (hasClass(b, beanClass)) {
                    if (exclude && b.getTarget().isInterface()){
                        continue;
                    }
                    result.add(b);
                }
            }
        }

        if (result.size() == 0) {
            return null;
        }

        if (result.size() > 1) {
            throw new Exception("多个" + beanClass.toString() + "的定义，请确保bean唯一！");
        }
        return result.get(0);
    }

    /**
     * 通过注解寻找符合的bean
     *
     * @param annotations
     * @return
     */
    public static Set<BeanDefinition> getBeanDefinitionsByAnnotation(Class... annotations) {
        if (classContainer.size() == 0) {
            return null;
        }
        Set<BeanDefinition> result = new HashSet<>();
        for (BeanDefinition beanDefinition : classContainer) {
            Class target = beanDefinition.getTarget();
            if (AnnotationUtil.hasAnnotion(target, annotations)) {
                result.add(beanDefinition);
            }
        }
        return result;
    }

    /**
     * 是否包含该class
     *
     * @param beanDefinition
     * @param beanClass
     * @return
     */
    public static boolean hasClass(BeanDefinition beanDefinition, Class beanClass) {
        if (beanClass == beanDefinition.getTarget()) {
            return true;
        }
        for (Class c : beanDefinition.getSuperClasses()) {
            if (c.equals(beanClass)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 注册一个bean
     *
     * @param beanName
     * @param beanClass
     * @return
     */
    @Override
    public boolean registerBean(String beanName, Class beanClass) {
        BeanDefinition beanDefinition = BeanDefinitionUtil.convertToBeanDefinition(beanClass);
        if (!isExist(beanName, beanDefinition)) {
            classContainer.add(beanDefinition);
            return true;
        }
        return false;
    }

    /**
     * 获取bean的实例
     *
     * @param beanName
     * @param target
     * @return
     * @throws Exception
     */
    @Override
    public Object getBean(String beanName, Class target) throws Exception {
        BeanDefinition beanDefinition = getBeanDefinition(beanName, target,true);
        ApplicationContextApi contextApi = beanDefinition.getContextApi();
        if (contextApi == null) {
            return null;
        }
        return contextApi.getBean(beanName, target);
    }
}
