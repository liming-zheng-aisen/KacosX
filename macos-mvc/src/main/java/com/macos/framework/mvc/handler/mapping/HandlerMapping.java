package com.macos.framework.mvc.handler.mapping;


import com.macos.common.util.StringUtils;
import com.macos.framework.mvc.context.ServletContext;
import com.macos.framework.mvc.enums.HttpMethod;
import com.macos.framework.mvc.handler.bean.RequestUrlBean;


/**
 * @author zheng.liming
 * @date 2019/8/18
 * @description
 */
public class HandlerMapping {

    /**
     * 获取请求的处理类
     *
     * @param url
     * @param httpMethod
     * @return
     */
    public RequestUrlBean requestMethod(String url, HttpMethod httpMethod) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        ServletContext servletContext = ServletContext.Builder.getServletContext();
        if (StringUtils.isEmptyPlus(url)) {
            return null;
        }
        url = StringUtils.formatUrl(url);
        String key = httpMethod + ":" + url;
        Object handler = servletContext.getBean(key, RequestUrlBean.class);
        return handler == null ? null : (RequestUrlBean) handler;
    }


}
