package com.macos.start.web.jetty.filter.init;


import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Order;
import com.macos.framework.annotation.WebFilter;
import com.macos.framework.context.ApplicationContextImpl;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.core.bean.factory.BeanFactory;
import com.macos.framework.core.bean.factory.ValueFactory;
import com.macos.framework.core.load.conf.ConfigurationLoader;
import com.macos.start.web.jetty.filter.FilterBean;
import com.macos.start.web.jetty.filter.WebFilterMannager;

import javax.servlet.Filter;
import java.util.HashSet;
import java.util.Set;

/**
 * @Desc DyFilterRegisterServer
 * @Author Zheng.LiMing
 * @Date 2019/9/7
 */
public class FilterRegisterServer {

    public void autoRegisterFilter(Set<Class> classes){

        try {

            Set<Class> result=getWebFilterClass(classes);

            ApplicationContextApi context= ApplicationContextImpl.Builder.getDySpringApplicationContext();

            for (Class c:result){
                if (isFilter(c)){
                    Integer orderNum=1;
                    if (c.isAnnotationPresent(Order.class)){
                        Order dyOrder=(Order)c.getAnnotation(Order.class);
                        orderNum=dyOrder.value();
                    }else{
                        orderNum = (int)Math.random()*10000;
                    }

                    WebFilter dyWebFilter = (WebFilter)c.getAnnotation(WebFilter.class);

                    String requestUrl=dyWebFilter.requestUrl();
                    String filterName=dyWebFilter.filterName();

                    if (StringUtils.isEmptyPlus(filterName)){
                        filterName=StringUtils.toLowerCaseFirstName(c.getSimpleName());
                    }

                    Filter filter = (Filter) BeanFactory.createNewBean(c);

                    ValueFactory.doFields(filter, ConfigurationLoader.getEvn());

                    registerFilter(filter,orderNum,requestUrl,filterName);

                    context.registerBean(filterName,filter);

                    String beanName = StringUtils.toLowerCaseFirstName(c.getSimpleName());
                    if (!beanName.equals(filterName)) {
                        context.registerBean(beanName, filter);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Set<Class> getWebFilterClass(Set<Class> ls){
        Set<Class> result=new HashSet<>();
        for (Class cl:ls){
            if (cl.isAnnotationPresent(WebFilter.class)){
                result.add(cl);
            }
        }
        return result;
    }

    private boolean isFilter(Class obj) {
        Class[] classes=obj.getInterfaces();
        for (Class c: classes) {
            if (c==Filter.class){
                return true;
            }
        }
        return false;
    }

    private void registerFilter(Filter filter, Integer order, String  url,String filterName){

        FilterBean filterBean=new FilterBean(filter);

        filterBean.setOrderNum(order);

        filterBean.setFilterName(filterName);

        filterBean.setUrl(url);

        WebFilterMannager.registerFilterBean(filterBean);

    }

}
