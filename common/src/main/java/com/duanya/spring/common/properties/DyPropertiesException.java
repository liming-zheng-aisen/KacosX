package com.duanya.spring.common.properties;

/**
 * @author zheng.liming
 * @date 2019/8/12
 * @description
 */
public class DyPropertiesException extends Exception {

    static final long serialVersionUID = 1 ;

    private String message;
    private Throwable cause;

    @Override
    public String getMessage() {
        return message;
    }

    public DyPropertiesException(String message) {
        super(message);
        this.message=message;
    }

    public DyPropertiesException(String message,Throwable cause) {
        super(message,cause);
        this.message=message;
        this.cause=cause;
    }
}
