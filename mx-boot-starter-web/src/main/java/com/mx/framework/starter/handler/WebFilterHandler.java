package com.mx.framework.starter.handler;

import com.mx.common.util.StringUtils;
import com.mx.framework.annotation.http.WebFilter;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.handle.base.BaseHandler;
import com.mx.framework.starter.bean.WebBeanDefinition;
import com.mx.framework.starter.web.filter.FilterBean;
import com.mx.framework.starter.web.filter.WebFilterMannager;

import javax.servlet.Filter;

/**
 * @Desc WebFilterHandler
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
public class WebFilterHandler extends BaseHandler {

    public WebFilterHandler() {
        handleAnnotations = new Class[]{WebFilter.class};
    }

    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {
        if (!needToHandle(handleClass)){
            return true;
        }

        if (doBefore(mainClass,handleClass,args)){
            WebFilter webFilter = (WebFilter)handleClass.getAnnotation(WebFilter.class);
            String requestUrl= webFilter.requestUrl();
            String filterName= webFilter.filterName();

            if (StringUtils.isEmptyPlus(filterName)){
                filterName=StringUtils.toLowerCaseFirstName(handleClass.getSimpleName());
            }

            WebBeanDefinition beanDefinition = (WebBeanDefinition) BeanManager.getBeanDefinition(null,handleClass,true);

            Filter filter = (Filter) newInstance(beanDefinition,null);

            registerFilter(filter,beanDefinition.getOrder(),requestUrl,filterName);

        }

        return doAfter(mainClass,handleClass,args);
    }

    private void registerFilter(Filter filter, Integer order, String  url, String filterName){

        FilterBean filterBean=new FilterBean(filter);

        filterBean.setOrderNum(order);

        filterBean.setFilterName(filterName);

        filterBean.setUrl(url);

        WebFilterMannager.registerFilterBean(filterBean);

    }
}
