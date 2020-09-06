package com.mx.framework.context.exception;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description
 */
public class ContextException extends Exception {

    static final long serialVersionUID = 1 ;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public ContextException(String message) {
        super(message);
        this.message=message;
    }


}
