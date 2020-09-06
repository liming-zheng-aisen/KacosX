package com.mx.aop.core.even.api;


/**
 * 事件定义，用于切面通知事件，一个有三个参数，R返回值，E事件对象，P参数
 * @Desc 事件定义
 * @Author Zheng.LiMing
 * @Date 2020/1/15
 */
public interface EvenApi<R,E,P> {
    /**
     * 回调
     * @param p 参数
     * @return R 返回处理结果
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
