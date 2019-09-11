package com.duanya.spring.framework.starter.load;

/**
 * @Desc DyStarterBean
 * @Author Zheng.LiMing
 * @Date 2019/9/11 starter执行对象
 */
public class DyStarterBean {
    private Integer order;
    private Class target;
    private String[] scannerPath;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Class getTarget() {
        return target;
    }

    public void setTarget(Class target) {
        this.target = target;
    }

    public String[] getScannerPath() {
        return scannerPath;
    }

    public void setScannerPath(String[] scannerPath) {
        this.scannerPath = scannerPath;
    }

    public DyStarterBean() {
    }

    public DyStarterBean(Integer order, Class target, String[] scannerPath) {
        this.order = order;
        this.target = target;
        this.scannerPath = scannerPath;
    }
}
