package com.duanya.spring.web;

import com.duanya.spring.framework.core.annotation.DyBootApplication;
import com.duanya.start.web.run.DyBootApplicationWeb;

/**
 * @Desc WebTestApplication
 * @Author Zheng.LiMing
 * @Date 2019/9/3
 */
@DyBootApplication
public class WebTestApplication {
    public static  void  main(String [] args){
        DyBootApplicationWeb.run(WebTestApplication.class);
    }
}
