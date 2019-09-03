package com.duanya.spring.web;

import com.duanya.spring.framework.core.annotation.DyBootApplication;
import com.duanya.start.web.run.DyBootApplicationWeb;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description
 */
@DyBootApplication
public class WebTestApplication {
    public static void main(String[] args) {
        DyBootApplicationWeb.run(WebTestApplication.class);
    }
}
