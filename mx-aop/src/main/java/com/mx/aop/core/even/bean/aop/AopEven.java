package com.mx.aop.core.even.bean.aop;
import com.mx.aop.core.even.bean.EvenBeanInfo;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Desc 切面事件注册信息
 * @Author Zheng.LiMing
 * @Date 2020/1/15
 */
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

    public String getMatching() {
        return matching;
    }

    public void setMatching(String matching) {
        this.matching = matching;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getCallback() {
        return callback;
    }

    public void setCallback(Method callback) {
        this.callback = callback;
    }
}
