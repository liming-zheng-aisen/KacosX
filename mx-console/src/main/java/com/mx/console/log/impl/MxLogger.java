package com.mx.console.log.impl;

import com.mx.console.log.Logger;
import com.mx.console.log.lazy.LogBody;
import com.mx.console.log.lazy.LogEnum;
import com.mx.console.log.lazy.LoggerLazy;

/**
 * @Desc MxLogger
 * @Author Zheng.LiMing
 * @Date 2020/8/20
 */
public class MxLogger<E> implements Logger<E> {
    /**
     * 默认是debug模式
     */
    private static Integer level = LogLevel.DEBUG_LEVEL;

    private static boolean isOpen = true;

    protected String clazz;

    public MxLogger(String clazz) {
        this.clazz = clazz;
    }


    @Override
    public void info(String msg, Object... param) {
        if (!isOpen || level < LogLevel.INFO_LEVEL) {
            return;
        }

        LogBody logBody = new LogBody(Thread.currentThread().getName(),LogEnum.INFO, clazz, newMsg(msg, param));
        LoggerLazy.pushLog(logBody);
    }

    @Override
    public void error(String msg, Object... param) {
        if (!isOpen || level < LogLevel.ERROR_LEVEL) {
            return;
        }

        LogBody logBody = new LogBody(Thread.currentThread().getName(),LogEnum.ERROR, clazz, newMsg(msg, param));
        LoggerLazy.pushLog(logBody);
    }

    @Override
    public void warn(String msg, Object... param) {
        if (!isOpen || level < LogLevel.WARN_LEVEL) {
            return;
        }

        LogBody logBody = new LogBody(Thread.currentThread().getName(),LogEnum.WARN, clazz, newMsg(msg, param));
        LoggerLazy.pushLog(logBody);
    }

    @Override
    public void debug(String msg, Object... param) {
        if (!isOpen || level < LogLevel.DEBUG_LEVEL) {
            return;
        }

        LogBody logBody = new LogBody(Thread.currentThread().getName(),LogEnum.DEBUG, clazz, newMsg(msg, param));
        LoggerLazy.pushLog(logBody);
    }

    @Override
    public void success(String msg, Object... param) {
        if (!isOpen) {
            return;
        }
        LogBody logBody = new LogBody(Thread.currentThread().getName(),LogEnum.SUCCESS, clazz, newMsg(msg, param));
        LoggerLazy.pushLog(logBody);
    }

    @Override
    public void changeLevel(Integer changeLevel) {
        level = changeLevel;
    }

    protected String newMsg(String msg, Object... param) {
        if (param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                String index = "{" + (i + 1) + "}";
                msg = msg.replace(index, String.valueOf(param[i]));
            }
        }
        return msg;
    }

    /**
     * 修改日志级别
     *
     * @param changeLevel
     */
    public static void setLevel(Integer changeLevel) {
        level = changeLevel;
    }

    public static void openLog() {
        isOpen = true;
    }

    public static void stopLog() {
        isOpen = false;
    }

}
