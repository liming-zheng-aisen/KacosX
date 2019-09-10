package com.duanya.spring.web.filter;

import com.duanya.spring.framework.annotation.DyOrder;
import com.duanya.spring.framework.annotation.DyWebFilter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Desc HelloFilter
 * @Author Zheng.LiMing
 * @Date 2019/9/7
 */
@DyWebFilter(requestUrl = "/*",filterName = "testFilter200")
@DyOrder(200)
public class TestFilter200 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("我是测试200");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("测试200结束");
    }
}
