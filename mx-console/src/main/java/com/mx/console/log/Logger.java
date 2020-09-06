package com.mx.console.log;

/**
 * @Desc 日志
 * @Author Zheng.LiMing
 * @Date 2020/8/20
 */
public interface Logger<E> {
    /**
     * 基本消息输出
     * @param msg
     * @param param
     */
    void info(String msg, Object... param);

    /**
     * 错误信息输出
     * @param msg
     * @param param
     */
    void error(String msg, Object... param);

    /**
     * 警告信息输出
     * @param msg
     * @param param
     */
    void warn(String msg, Object... param);

    /**
     * 调试信息输出
     * @param msg
     * @param param
     */
    void debug(String msg, Object... param);

    /**
     * 系统输出
     * @param msg
     * @param param
     */
    void success(String msg, Object... param);

    /**
     * 设置日记级别
     * @param level
     */
    void changeLevel(Integer level);
}
