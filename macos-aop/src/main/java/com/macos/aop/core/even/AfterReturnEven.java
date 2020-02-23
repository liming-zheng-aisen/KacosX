package com.macos.aop.core.even;


import com.macos.aop.core.even.api.EvenApi;
import com.macos.aop.core.even.bean.EvenData;
import com.macos.aop.core.even.bean.aop.AopEven;

/**
 * @Desc 后置通知（返回通知）
 * @Author Zheng.LiMing
 * @Date 2020/2/2
 */
public class AfterReturnEven implements EvenApi<Void,AopEven,EvenData> {

    public AfterReturnEven(AopEven aopEven) {
        this.aopEven = aopEven;
    }

    /**
     *切面事件
     */
    private AopEven aopEven;

    @Override
    public Void callback(EvenData evenData) throws Exception {
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
