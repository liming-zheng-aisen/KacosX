package com.mx.framework.core.handle;

import com.mx.aop.core.enums.AopGroupEnum;
import com.mx.aop.core.even.bean.aop.AopEven;
import com.mx.aop.core.mananger.AopEvenMananger;
import com.mx.aop.core.mananger.PointCupMananger;
import com.mx.common.util.ReflectionsUtil;
import com.mx.framework.annotation.aop.AfterReturn;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.handle.base.BaseHandler;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Desc 后置通知
 * @Author Zheng.LiMing
 * @Date 2020/4/2
 */
public class AfterReturnHandler extends BaseHandler {

    public AfterReturnHandler() {
        handleAnnotations = new Class[]{AfterReturn.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if(!doBefore(mainClass,handleClass,args)){
            return false;
        }

        BeanManager beanManager = new BeanManager();
        Object target = beanManager.getBean(null,handleClass);

        List<Method> methods = ReflectionsUtil.getMethodsByAnnotation(handleClass,AfterReturn.class);

        for (Method method : methods){
            AfterReturn afterReturn = method.getAnnotation(AfterReturn.class);
            String matching = PointCupMananger.get(afterReturn.pointcup());
            AopEven aopEven = new AopEven();
            aopEven.setBean(target);
            aopEven.setCallback(method);
            aopEven.setMatching(matching);
            AopEvenMananger.registerEven(AopGroupEnum.AfterReturn,aopEven);
        }
        return doAfter(mainClass,handleClass,args);
    }
}
