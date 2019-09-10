package com.duanya.spring.framework.mvc.handler.mapping;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.mvc.context.DyServletContext;
import com.duanya.spring.framework.mvc.enums.DyMethod;
import com.duanya.spring.framework.mvc.handler.bean.RequestUrlBean;

import java.util.Map;

/**
 * @author zheng.liming
 * @date 2019/8/18
 * @description
 */
public class HandlerMapping {

    public  RequestUrlBean requestMethod(String url,DyMethod dymethod){

        Map<String,RequestUrlBean> servletContext= DyServletContext.getServletContext();

        if (StringUtils.isEmptyPlus(url)||servletContext.size()==0){
            return null;
        }

        url=StringUtils.formatUrl(url);

        String key=url+dymethod;

        if (servletContext.containsKey(key)){
            return servletContext.get(key);
        }else {
            key = url.substring(0, url.lastIndexOf("/")+1)+"*"+dymethod;
            RequestUrlBean requestUrlBean= servletContext.get(key);
            if (null!=requestUrlBean){
                if (requestUrlBean.isBringParam()){
                    return requestUrlBean;
                }
            }
             return null;
            }

    }


}
