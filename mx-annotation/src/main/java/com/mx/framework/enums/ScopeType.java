package com.mx.framework.enums;

/**
 * @Desc 作用域,用于注入对象时，判断是否需要单例
 * @Author Zheng.LiMing
 * @Date 2019/12/31
 */
public enum  ScopeType {
    PROTOTYPE(true,"原型模式"),
    SINGLETON(false,"单例模式");

    /**
     * 是否单例模式
     */
    private boolean Prototype;

    /**
     * 文档说明
     */
    private String dom;

    ScopeType(boolean prototype, String dom) {
        Prototype = prototype;
        this.dom = dom;
    }

    public boolean isPrototype() {
        return Prototype;
    }

    public void setPrototype(boolean prototype) {
        Prototype = prototype;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }
}
