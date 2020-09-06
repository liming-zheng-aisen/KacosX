package com.mx.aop.core.even;

import com.mx.aop.core.even.api.EvenApi;
import com.mx.aop.core.even.bean.EvenBeanReturn;
import com.mx.aop.core.even.bean.aop.AopEven;

/**
 * @Desc 环绕通知
 * @Author Zheng.LiMing
 * @Date 2020/1/15
 */
public class AroundEven implements EvenApi<Object, AopEven, EvenBeanReturn> {

    public AroundEven(AopEven aopEven) {
        this.aopEven = aopEven;
    }

    /**
     *切面事件
     */
    private AopEven aopEven;

    /**
     * 环绕通知回调
     * @return 返回值
     */
    @Override
    public Object callback(EvenBeanReturn evenBeanReturn) throws Exception {
        return aopEven.callback(evenBeanReturn);
    }

    @Override
    public AopEven getEvenBean() {
        return aopEven;
    }

    @Override
    public void setEvenBean(AopEven aopEven) {
        this.aopEven = aopEven;
    }

}
