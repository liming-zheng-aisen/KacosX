package com.duanya.start.web.jetty.filter.init;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyOrder;
import com.duanya.spring.framework.annotation.DyWebFilter;
import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import com.duanya.spring.framework.core.bean.factory.DyValueFactory;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import com.duanya.start.web.jetty.filter.DyFilterBean;
import com.duanya.start.web.jetty.filter.DyWebFilterMannager;

import javax.servlet.Filter;
import java.util.HashSet;
import java.util.Set;

/**
 * @Desc DyFilterRegisterServer
 * @Author Zheng.LiMing
 * @Date 2019/9/7
 */
public class DyFilterRegisterServer {

    public void autoRegisterFilter(Set<Class> classes){

        try {

            Set<Class> result=getWebFilterClass(classes);

            DySpringApplicationContext context=DySpringApplicationContext.Builder.getDySpringApplicationContext();

            for (Class c:result){
                if (isFilter(c)){
                    Integer orderNum=1;
                    if (c.isAnnotationPresent(DyOrder.class)){
                        DyOrder dyOrder=(DyOrder)c.getAnnotation(DyOrder.class);
                        orderNum=dyOrder.value();
                    }else{
                        orderNum = (int)Math.random()*10000;
                    }

                    DyWebFilter dyWebFilter = (DyWebFilter)c.getAnnotation(DyWebFilter.class);

                    String requestUrl=dyWebFilter.requestUrl();
                    String filterName=dyWebFilter.filterName();

                    if (StringUtils.isEmptyPlus(filterName)){
                        filterName=StringUtils.toLowerCaseFirstName(c.getSimpleName());
                    }

                    Filter filter = (Filter) DyBeanFactory.createNewBean(c);

                    DyValueFactory.doFields(filter,DyConfigurationLoader.getEvn());

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
            if (cl.isAnnotationPresent(DyWebFilter.class)){
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

        DyFilterBean filterBean=new DyFilterBean(filter);

        filterBean.setOrderNum(order);

        filterBean.setFilterName(filterName);

        filterBean.setUrl(url);

        DyWebFilterMannager.registerFilterBean(filterBean);

    }

}
