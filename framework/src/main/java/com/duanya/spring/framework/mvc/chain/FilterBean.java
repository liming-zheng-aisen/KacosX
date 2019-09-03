package com.duanya.spring.framework.mvc.chain;

import com.duanya.spring.commont.util.StringUtils;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zheng.liming
 * @date 2019/9/2
 * @description
 */
public class FilterBean {
    public List<String> regex=new ArrayList<>();
    public Filter filter;

    public FilterBean(Filter filter) {
        this.filter = filter;
    }

    public List<String> getRegex() {
        return regex;
    }

    public void addUrl(String url) {
       if (StringUtils.isEmptyPlus(url)){
           return;
       }
       url=url.replace("/","\\/");
       url=url.replace("*",".*");
       regex.add(url);
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
