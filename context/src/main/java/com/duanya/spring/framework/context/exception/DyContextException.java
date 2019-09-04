package com.duanya.spring.framework.context.exception;

/**
 * @author zheng.liming
 * @date 2019/8/5
 * @description
 */
public class DyContextException extends Exception {

    static final long serialVersionUID = 1 ;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public DyContextException(String message) {
        super(message);
        this.message=message;
    }


}
