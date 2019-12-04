package com.macos.framework.mvc.handler.mapping;


import com.duanya.spring.framework.mvc.handler.bean.RequestUrlBean;
import com.macos.common.util.StringUtils;
import com.macos.framework.mvc.context.ServletContext;
import com.macos.framework.mvc.enums.HttpMethod;

import java.util.Map;

/**
 * @author zheng.liming
 * @date 2019/8/18
 * @description
 */
public class HandlerMapping {

    public  RequestUrlBean requestMethod(String url, HttpMethod dymethod){

        Map<String,RequestUrlBean> servletContext= ServletContext.getServletContext();

        if (StringUtils.isEmptyPlus(url)||servletContext.size()==0){
            return null;
        }

        url=StringUtils.formatUrl(url);

        String key=url+dymethod;

        if (servletContext.containsKey(key)){
            return servletContext.get(key);
        }else {
            if (url.length()>1) {
                key = url.substring(0, url.lastIndexOf("/") + 1) + "*" + dymethod;
                RequestUrlBean requestUrlBean = servletContext.get(key);
                if (null != requestUrlBean) {
                    if (requestUrlBean.isBringParam()) {
                        return requestUrlBean;
                    }
                }
            }
             return null;
            }

    }


}
