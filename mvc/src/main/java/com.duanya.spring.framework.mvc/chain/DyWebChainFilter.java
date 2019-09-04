package com.duanya.spring.framework.mvc.chain;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description
 */
public class DyWebChainFilter implements FilterChain {

    private Filter nextFilter;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {

    }

}
