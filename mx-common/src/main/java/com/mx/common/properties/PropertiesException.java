package com.mx.common.properties;

/**
 * @author zheng.liming
 * @date 2019/8/12
 * @description
 */
public class PropertiesException extends Exception {

    static final long serialVersionUID = 1 ;

    private String message;
    private Throwable cause;

    @Override
    public String getMessage() {
        return message;
    }

    public PropertiesException(String message) {
        super(message);
        this.message=message;
    }

    public PropertiesException(String message, Throwable cause) {
        super(message,cause);
        this.message=message;
        this.cause=cause;
    }
}
