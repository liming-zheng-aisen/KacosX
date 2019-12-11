package com.macos.framework.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author Aisen
 * @mail aisen.zheng.tech@linkkt.one
 * @creater 2019/12/11 11:58:44
 * @desc
 */
public class JdbcMapperProxy extends Proxy {
    /**
     * Constructs a new {@code Proxy} instance from a subclass
     * (typically, a dynamic proxy class) with the specified value
     * for its invocation handler.
     *
     * @param h the invocation handler for this proxy instance
     * @throws NullPointerException if the given invocation handler, {@code h},
     *                              is {@code null}.
     */
    protected JdbcMapperProxy(InvocationHandler h) {
        super(h);
    }

}
