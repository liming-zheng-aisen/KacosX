package com.macos.framework.starter.handler;

import com.macos.framework.annotation.WebServlet;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.handle.base.BaseHandler;
import com.macos.framework.starter.web.servlet.ServletBean;
import com.macos.framework.starter.web.servlet.ServletBeanInitManager;

import javax.servlet.Servlet;


/**
 * @Desc WebServletHandler
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class WebServletHandler extends BaseHandler {

    public WebServletHandler() {
        handleAnnotations = new Class[]{WebServlet.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {

        if (!needToHandle(handleClass)){
            return true;
        }

        if (doBefore(mainClass,handleClass,args)){
            WebServlet webServlet=(WebServlet) handleClass.getAnnotation(WebServlet.class);
            String url=webServlet.url();
            registerServlet(handleClass,url);
        }

        return doAfter(mainClass,handleClass,args);


    }

    public void registerServlet(Class servlet,String url){
        try {
            BeanDefinition beanDefinition =  BeanManager.getBeanDefinition(null,servlet,true);
            Servlet servlet1= (Servlet) newInstance(beanDefinition,null);
            ServletBean servletBean = new ServletBean(servlet1,url);
            ServletBeanInitManager.put(servletBean);
            } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
