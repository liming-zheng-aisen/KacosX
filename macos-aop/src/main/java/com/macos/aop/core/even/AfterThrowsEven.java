package com.macos.aop.core.even;

import com.macos.aop.core.even.api.EvenApi;
import com.macos.aop.core.even.bean.EvenThrowsException;
import com.macos.aop.core.even.bean.aop.AopEven;

/**
 * @Desc 异常通知
 * @Author Zheng.LiMing
 * @Date 2020/2/2
 */
public class AfterThrowsEven implements EvenApi<Void,AopEven,EvenThrowsException> {
    public AfterThrowsEven(AopEven aopEven) {
        this.aopEven = aopEven;
    }

    /**
     *切面事件
     */
    private AopEven aopEven;

    @Override
    public Void callback(EvenThrowsException evenData) throws Exception {
        aopEven.callback(evenData);
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
