package com.mx.console.log.lazy;

/**
 * @Desc LogBody
 * @Author Zheng.LiMing
 * @Date 2020/8/20
 */
public class LogBody {

    private String threadName;

    private LogEnum logType;

    private String title;

    private String msg;

    public LogBody(String threadName,LogEnum logType,String title, String msg) {
       this.threadName = threadName;
        this.logType = logType;
        this.title = title;
        this.msg = msg;
    }

    public LogBody() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LogEnum getLogType() {
        return logType;
    }

    public void setLogType(LogEnum logType) {
        this.logType = logType;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }
}
