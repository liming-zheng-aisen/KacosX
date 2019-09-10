package com.duanya.start.web.jetty.filter;

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
public class DyWebFilterMannager {

    private static Set<DyFilterBean> webFilters=new LinkedHashSet<>();


    public static  Set<DyFilterBean> getWebFilters(){
        return webFilters;
    }


    public static void registerFilterBean(DyFilterBean filter){
        webFilters.add(filter);
    }


    public static void  registerFilterBeanList( List<DyFilterBean> filterBeanList){
        webFilters.addAll(filterBeanList);
    }

    public static boolean hasWebFilters(){
        return webFilters.size()>0;
    }

    public synchronized static  Set<DyFilterBean> getFilterBeanSet() {

            if (hasWebFilters()) {

               return webFilters.stream().sorted(Comparator.comparing(DyFilterBean::getOrderNum)).collect(Collectors.toSet());
            }

            return null;
    }



}
