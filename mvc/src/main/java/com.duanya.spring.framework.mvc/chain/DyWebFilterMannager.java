package com.duanya.spring.framework.mvc.chain;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zheng.liming
 * @date 2019/8/25
 * @description
 */
public class DyWebFilterMannager {

    private static List<Filter> webFilters=new ArrayList<>();

    public static List<Filter> getWebFilters(){
        return webFilters;
    }

    public static void registerFilter(int index,Filter filter){
        webFilters.add(index,filter);
    }

    public static void  registerFilter(Filter filter){
        webFilters.add(filter);
    }
}
