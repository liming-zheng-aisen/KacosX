package com.duanya.spring.common.times;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description
 */
public class DyTimer {
    private long start;
    public long doStart(){
        start=System.currentTimeMillis();
        return start;
    }
    public long spendingTime(){
        return System.currentTimeMillis()-start;
    }
}
