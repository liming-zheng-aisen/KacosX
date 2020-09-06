package com.mx.framework.starter.web.log;

import com.mx.console.log.factory.LoggerFactory;
import com.mx.console.log.impl.LogLevel;
import org.eclipse.jetty.util.log.Logger;

/**
 * @Desc JettyLogger
 * @Author Zheng.LiMing
 * @Date 2020/8/21
 */
public class JettyLogger implements Logger {

    private final static String NAME = "MX-Jetty-LOG";

    private final static String KH = "{}";

    private Class ignored = null;

    private boolean isDebug = true;

    private com.mx.console.log.Logger log = LoggerFactory.buildLogger(NAME);

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void warn(String msg, Object... args) {
        log.warn(formatMsg(msg), args);
    }

    @Override
    public void warn(Throwable thrown) {
        log.warn(getThrowableMsg(thrown));
    }

    @Override
    public void warn(String msg, Throwable thrown) {
        log.warn(msg);
        warn(thrown);
    }

    @Override
    public void info(String msg, Object... args) {
        log.info(formatMsg(msg),args);
    }

    @Override
    public void info(Throwable thrown) {
        log.info(getThrowableMsg(thrown));
    }

    @Override
    public void info(String msg, Throwable thrown) {
        log.info(msg);
        info(thrown);
    }

    @Override
    public boolean isDebugEnabled() {
        return isDebug;
    }

    @Override
    public void setDebugEnabled(boolean enabled) {
        if (enabled){
            log.changeLevel(LogLevel.DEBUG_LEVEL);
        }else {
            log.changeLevel(LogLevel.INFO_LEVEL);
        }
        this.isDebug = enabled;
    }

    @Override
    public void debug(String msg, Object... args) {
      log.debug(formatMsg(msg),args);
    }

    @Override
    public void debug(String msg, long value) {
        log.debug(formatMsg(msg),value);
    }

    @Override
    public void debug(Throwable thrown) {
        log.debug(getThrowableMsg(thrown));
    }

    @Override
    public void debug(String msg, Throwable thrown) {
        log.debug(msg);
        log.debug(getThrowableMsg(thrown));
    }

    @Override
    public Logger getLogger(String name) {
        return this;
    }

    @Override
    public void ignore(Throwable ignored) {
        this.ignored = ignored.getClass();
    }

    private boolean isIgnore(Throwable throwable) {
        if (throwable != null && throwable.getClass() == this.ignored) {
            return true;
        }
        return false;
    }

    private String formatMsg(String msg) {
        boolean s = false;
        int i = 1;
        while (msg.indexOf(KH) >= 0) {
            int index = msg.indexOf(KH);
            msg = msg.substring(0, index + 1) + i + msg.substring(index + 1);
            i++;
        }
        return msg;
    }

    private String getThrowableMsg(Throwable thrown){
        StringBuffer stringBuffer = new StringBuffer("$" + thrown.getClass().getName());
        for (StackTraceElement element : thrown.getStackTrace()) {
            stringBuffer.append(" \n \t定位 - " + element.toString());
        }
        return stringBuffer.toString();
    }

}
