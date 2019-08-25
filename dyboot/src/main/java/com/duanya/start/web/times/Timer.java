package com.duanya.start.web.times;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description
 */
public class Timer {
    private long start;

    public long doStart(){
        start=System.currentTimeMillis();
        return start;
    }

    public long spendingTime(){
        return System.currentTimeMillis()-start;
    }
}
