package com.macos.start.web.jetty.filter;



import com.macos.common.util.StringUtils;

import javax.servlet.Filter;

/**
 * @author zheng.liming
 * @date 2019/9/2
 * @description
 */
public class FilterBean {

    private Integer orderNum;

    private String url;

    private Filter filter;

    private String filterName;

    public FilterBean(Filter filter) {
        this.filter = filter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url=(StringUtils.trim(url));
    }

    public Filter getFilter() {
        return filter;
    }


    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
}
