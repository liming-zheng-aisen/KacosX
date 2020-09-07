package com.mx.console.log.transaction;

import com.mx.console.log.Logger;
import com.mx.console.log.impl.MxLogger;
import com.mx.console.log.lazy.LogBody;
import com.mx.console.log.lazy.LogEnum;
import com.mx.console.log.lazy.LoggerLazy;

import java.util.ArrayList;
import java.util.List;

public class LoggerTransaction extends MxLogger<Class> {

    private ThreadLocal<List<LogBody>> transactionLogList;

    private LoggerTransaction(String clazzName) {
        super(clazzName);
        transactionLogList = new ThreadLocal<>();
        transactionLogList.set(new ArrayList<>());
        transactionLogList.get().add(new LogBody(Thread.currentThread().getName(), LogEnum.SUCCESS, clazzName,"[begin]{"));
    }

    public synchronized static LoggerTransaction begin(String method){
       return new LoggerTransaction(method);
    }

    public synchronized void commit(){
        transactionLogList.get().add(new LogBody("", LogEnum.SUCCESS,"","}[end]"));
        LoggerLazy.pushLog(transactionLogList.get());
    }

    public static void main(String[] args) throws InterruptedException {
        LoggerTransaction log = LoggerTransaction.begin("main");
        log.commit();
        Thread.sleep(2000L);
    }




}
