package com.duanya.start.web.jetty.servlet;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyWebServlet;
import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.bean.factory.DyValueFactory;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;

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

    private DySpringApplicationContext applicationContext=DySpringApplicationContext.Builder.getDySpringApplicationContext();

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
            DyValueFactory.doFields(servlet1,DyConfigurationLoader.getEvn());
            DyServletBean dyServletBean=new DyServletBean(servlet1,url);
            servletBeans.add(dyServletBean);
            //交给applicationContext管理，因为Servlet有可能需要注入
            applicationContext.registerBean(StringUtils.toLowerCaseFirstName(servlet.getSimpleName()),servlet1);
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
