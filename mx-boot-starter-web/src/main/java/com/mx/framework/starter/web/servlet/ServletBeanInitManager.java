package com.mx.framework.starter.web.servlet;

import java.util.HashSet;
import java.util.Set;

/**
 * @Desc  Servlet管理
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class ServletBeanInitManager {

    private static Set<ServletBean> servletBeans=new HashSet<>();

    public static Set<ServletBean> getServletBeans() {
        return servletBeans;
    }

    public static void put(ServletBean servletBean){
        servletBeans.add(servletBean);
    }

}
