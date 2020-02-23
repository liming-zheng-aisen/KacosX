package com.macos.aop.proxy;


import com.macos.aop.core.enums.AopGroupEnum;
import com.macos.aop.core.even.AfterFinalEven;
import com.macos.aop.core.even.AfterReturnEven;
import com.macos.aop.core.even.BeforeEven;
import com.macos.aop.core.even.api.EvenApi;
import com.macos.aop.core.even.bean.EvenBeanInfo;
import com.macos.aop.core.even.bean.EvenBeanReturn;
import com.macos.aop.core.even.bean.EvenData;
import com.macos.aop.core.even.bean.EvenThrowsException;
import com.macos.aop.core.even.bean.aop.AopEven;
import com.macos.aop.core.mananger.AopEvenMananger;
import com.macos.common.util.ThreadServiceUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @Desc 实例化对象代理
 * @Author Zheng.LiMing
 * @Date 2019/9/15
 */
public class BeanProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        EvenBeanInfo evenBeanInfo = new EvenBeanInfo(target,method,args);
        //前置通知
        notifyBefore(evenBeanInfo);
        try {
            //环绕通知
            EvenBeanReturn evenBeanReturn = new EvenBeanReturn(target,method,args,methodProxy);
            Object data = notifyAround(evenBeanReturn);
            //后置通知
            EvenData evenData = new EvenData(target,method,args,data);
            notifyAfterReturn(evenData);
            return data;
        }catch (Exception e){
            //异常通知
            EvenThrowsException evenThrowsException = new EvenThrowsException(target,method,args,e);
            notifyAfterThrows(evenThrowsException);
            throw e;
        }finally {
            //最终通知
            notifyAfteFinal(evenBeanInfo);
        }
    }


    /**
     * 前置通知
     * @param evenBeanInfo
     */
    private void notifyBefore(EvenBeanInfo evenBeanInfo){
        Set<AopEven> aopEvens = AopEvenMananger.findEvens(AopGroupEnum.Before,evenBeanInfo.getMatches());
        if (aopEvens==null || aopEvens.size()==0){
            return;
        }
        for (AopEven aopEven : aopEvens) {
            BeforeEven beforeEven = new BeforeEven(aopEven);
            notifyAop(beforeEven,evenBeanInfo);
        }
    }

    /**
     * 后置通知（返回通知）
     * @param data
     */
    private void notifyAfterReturn(EvenData data){
        Set<AopEven> aopEvens = AopEvenMananger.findEvens(AopGroupEnum.After_return,data.getMatches());
        if (aopEvens==null || aopEvens.size()==0){
            return;
        }
        for (AopEven aopEven : aopEvens) {
            AfterReturnEven afterReturnEven = new AfterReturnEven(aopEven);
            notifyAop(afterReturnEven,data);
        }
    }

    /**
     * 异常通知
     * @param e
     */
    private void notifyAfterThrows(EvenThrowsException e){
        Set<AopEven> aopEvens = AopEvenMananger.findEvens(AopGroupEnum.After_throwing,e.getMatches());
        if (aopEvens==null || aopEvens.size()==0){
            return;
        }
        for (AopEven aopEven : aopEvens) {
            AfterReturnEven afterReturnEven = new AfterReturnEven(aopEven);
            notifyAop(afterReturnEven,e);
        }
    }



    /**
     * 最终通知
     * @param evenBeanInfo
     */
    private void notifyAfteFinal(EvenBeanInfo evenBeanInfo){
        Set<AopEven> aopEvens = AopEvenMananger.findEvens(AopGroupEnum.After_return,evenBeanInfo.getMatches());
        if (aopEvens==null || aopEvens.size()==0){
            return;
        }
        for (AopEven aopEven : aopEvens) {
            AfterFinalEven afterFinalEven = new AfterFinalEven(aopEven);
            notifyAop(afterFinalEven,evenBeanInfo);
        }
    }

    /**
     * 环绕通知
     * @param evenBeanReturn
     */
    private Object notifyAround(EvenBeanReturn evenBeanReturn) throws Throwable {
        Set<AopEven> aopEvens = AopEvenMananger.findEvens(AopGroupEnum.After_return,evenBeanReturn.getMatches());

        if (aopEvens==null || aopEvens.size()==0){
            return evenBeanReturn.invoke();
        }
        return aopEvens.iterator().next().callback(evenBeanReturn);
    }



    /**
     * 异步通知
     * @param evenApi
     * @param evenBeanInfo
     */
    private void  notifyAop(EvenApi evenApi , EvenBeanInfo evenBeanInfo){
        ThreadServiceUtil.execute(() -> {
            try {
                evenApi.callback(evenBeanInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
