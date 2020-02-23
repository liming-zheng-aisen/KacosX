package com.macos.aop.core.even;


import com.macos.aop.core.even.api.EvenApi;
import com.macos.aop.core.even.bean.EvenBeanInfo;
import com.macos.aop.core.even.bean.aop.AopEven;

/**
 * @Desc 前置通知
 * @Author Zheng.LiMing
 * @Date 2020/1/25
 */
public class BeforeEven implements EvenApi<Void,AopEven,EvenBeanInfo> {

    public BeforeEven(AopEven aopEven) {
        this.aopEven = aopEven;
    }

    /**
     *切面事件
     */
    private AopEven aopEven;


    @Override
    public Void callback(EvenBeanInfo info) throws Exception {
        aopEven.callback(info);
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
