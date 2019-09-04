package com.duanya.spring.web;

import com.duanya.spring.framework.annotation.DyBootApplication;
import com.duanya.spring.framework.starter.run.DyBootApplicationRun;

/**
 * @Desc WebTestApplication
 * @Author Zheng.LiMing
 * @Date 2019/9/3
 */
@DyBootApplication
public class WebTestApplication {
    public static  void  main(String [] args){
        DyBootApplicationRun.run(WebTestApplication.class);
    }
}
