package com.macos.aop.core.even.api;


/**
 * @Desc 事件定义
 * @Author Zheng.LiMing
 * @Date 2020/1/15
 */
public interface EvenApi<R,E,P> {
    /**
     * 回调
     *
     * @return
     * @throws Exception
     */
    R callback(P p) throws Exception;

    /**
     * 获取事件信息对象
     *
     * @return
     */
    E getEvenBean();

    /**
     * 设置事件信息对象
     *
     * @param e
     */
    void setEvenBean(E e);

}
