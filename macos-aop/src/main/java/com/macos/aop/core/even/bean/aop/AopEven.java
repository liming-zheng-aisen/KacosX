package com.macos.aop.core.even.bean.aop;
import com.macos.aop.core.even.bean.EvenBeanInfo;
import lombok.Data;
import lombok.ToString;


import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Desc 切面事件注册信息
 * @Author Zheng.LiMing
 * @Date 2020/1/15
 */
@Data
@ToString
public class AopEven {

    private String matching;

    private Object bean;

    private Method callback;

    public Object callback(EvenBeanInfo evenBean) throws Exception {
        Parameter[] parameters =  callback.getParameters();
        if (parameters.length==0){
            return callback.invoke(bean);
        }
        return callback.invoke(bean,evenBean);
    }

}
