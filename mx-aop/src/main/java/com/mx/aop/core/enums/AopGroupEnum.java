package com.mx.aop.core.enums;

/**
 * @Desc 切面通知说明
 * @Author Zheng.LiMing
 * @Date 2020/1/24
 */
public enum  AopGroupEnum {
    /**
     * 前置通知
     */
    Before,
    /**
     * 后置通知，正常返回，异常则不通知
     */
    AfterReturn,
    /**
     * 异常通知
     */
    AfterThrowing,
    /**
     *
     */
    AfterFinal,
    Around,
    Introduction
}
