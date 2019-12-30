package com.macos.framework.enums;

/**
 * @Desc ScopeType
 * @Author Zheng.LiMing
 * @Date 2019/12/31
 */
public enum  ScopeType {
    PROTOTYPE(true,"原型模式"),
    SINGLETON(false,"单例模式");

    private boolean Prototype;
    private String doc;

    ScopeType(boolean prototype, String doc) {
        Prototype = prototype;
        this.doc = doc;
    }

    public boolean isPrototype() {
        return Prototype;
    }

    public void setPrototype(boolean prototype) {
        Prototype = prototype;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }
}
