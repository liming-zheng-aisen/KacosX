package com.mx.framework.starter.web.filter;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zheng.liming
 * @date 2019/8/25
 * @description
 */
public class WebFilterMannager {

    private static Set<FilterBean> webFilters=new LinkedHashSet<>();


    public static  Set<FilterBean> getWebFilters(){
        return webFilters;
    }


    public static void registerFilterBean(FilterBean filter){
        webFilters.add(filter);
    }


    public static void  registerFilterBeanList( List<FilterBean> filterBeanList){
        webFilters.addAll(filterBeanList);
    }

    public static boolean hasWebFilters(){
        return webFilters.size()>0;
    }

    public synchronized static  Set<FilterBean> getFilterBeanSet() {

            if (hasWebFilters()) {

               return webFilters.stream().sorted(Comparator.comparing(FilterBean::getOrderNum)).collect(Collectors.toSet());
            }

            return null;
    }



}
