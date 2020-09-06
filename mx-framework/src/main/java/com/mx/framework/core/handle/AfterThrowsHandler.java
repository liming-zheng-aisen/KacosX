package com.mx.framework.core.handle;

import com.mx.aop.core.enums.AopGroupEnum;
import com.mx.aop.core.even.bean.aop.AopEven;
import com.mx.aop.core.mananger.AopEvenMananger;
import com.mx.aop.core.mananger.PointCupMananger;
import com.mx.common.util.ReflectionsUtil;
import com.mx.framework.annotation.aop.AfterThrows;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.handle.base.BaseHandler;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Desc 异常通知
 * @Author Zheng.LiMing
 * @Date 2020/4/2
 */
public class AfterThrowsHandler extends BaseHandler {

    public AfterThrowsHandler() {
        handleAnnotations = new Class[]{AfterThrows.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {
        if(!doBefore(mainClass,handleClass,args)){
            return false;
        }

        BeanManager beanManager = new BeanManager();
        Object target = beanManager.getBean(null,handleClass);

        List<Method> methods = ReflectionsUtil.getMethodsByAnnotation(handleClass,AfterThrows.class);

        for (Method method : methods){
            AfterThrows afterReturn = method.getAnnotation(AfterThrows.class);
            String matching = PointCupMananger.get(afterReturn.pointcup());
            AopEven aopEven = new AopEven();
            aopEven.setBean(target);
            aopEven.setCallback(method);
            aopEven.setMatching(matching);
            AopEvenMananger.registerEven(AopGroupEnum.AfterThrowing,aopEven);
        }
        return doAfter(mainClass,handleClass,args);
    }
}
