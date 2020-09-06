package com.mx.aop.core.even;


import com.mx.aop.core.even.api.EvenApi;
import com.mx.aop.core.even.bean.EvenBeanInfo;
import com.mx.aop.core.even.bean.aop.AopEven;

/**
 * @Desc 最终通知
 * @Author Zheng.LiMing
 * @Date 2020/2/2
 */
public class AfterFinalEven implements EvenApi<Void,AopEven,EvenBeanInfo> {

    public AfterFinalEven(AopEven aopEven) {
        this.aopEven = aopEven;
    }
    /**
     *切面事件
     */
    private AopEven aopEven;

    @Override
    public Void callback(EvenBeanInfo evenBeanInfo) throws Exception {
         aopEven.callback(evenBeanInfo);
         return null;
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
