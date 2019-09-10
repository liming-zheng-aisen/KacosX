package com.duanya.start.web.jetty.servlet;

import com.duanya.spring.framework.annotation.DyWebServlet;
import com.duanya.spring.framework.core.bean.factory.DyAutowiredFactory;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.bean.factory.DyValueFactory;
import com.duanya.spring.framework.mvc.dispatcher.DyDispatchedServlet;

import javax.servlet.Servlet;
import java.util.HashSet;
import java.util.Set;

/**
 * @Desc DyServletBeanInitManager
 * @Author Zheng.LiMing
 * @Date 2019/9/11
 */
public class DyServletBeanInitManager {

    private Set<DyServletBean> servletBeans=new HashSet<>();

    public void init(Set<Class> classes){
        for (Class c:classes){
            if (c.isAnnotationPresent(DyWebServlet.class)){
                DyWebServlet webServlet=(DyWebServlet) c.getAnnotation(DyWebServlet.class);
                String url=webServlet.url();
                registerServlet(c,url);
            }
        }
    }

    public void registerServlet(Class servlet,String url){

        if (hasServlet(servlet)){
            return;
        }

        try {
            Servlet servlet1=(Servlet)DyBeanFactory.createNewBean(servlet);
            DyAutowiredFactory.doAutowired(servlet1);
            DyValueFactory.doFields(servlet1,DyDispatchedServlet.getEvn());
            DyServletBean dyServletBean=new DyServletBean(servlet1,url);
            servletBeans.add(dyServletBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean hasServlet(Class servlet){
        for (DyServletBean servletBean:servletBeans){
            if (servletBean.getServlet().getClass()==servlet){
                return true;
            }
        }
        return false;
    }

    public Set<DyServletBean> getServletBeans() {
        return servletBeans;
    }

    private DyServletBeanInitManager(){

    }

    public static class Builder{
        private final static DyServletBeanInitManager dyServletBeanInitManager=new DyServletBeanInitManager();
        public static DyServletBeanInitManager getDyServletBeanInitManager(){
            return dyServletBeanInitManager;
        }
    }
}
