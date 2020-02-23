package com.macos.framework.mvc.util;

import com.macos.common.util.StringUtils;
import com.macos.framework.context.exception.ContextException;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.enums.HttpMethod;
import com.macos.framework.mvc.context.ServletContext;
import com.macos.framework.mvc.context.exception.ServletException;
import com.macos.framework.mvc.handler.bean.RequestUrlBean;

import java.lang.reflect.Method;

/**
 * @Desc HttpStringUtil
 * @Author Zheng.LiMing
 * @Date 2020/2/14
 */
public class HttpServerUtil {

    private static ServletContext servletContext = ServletContext.Builder.getServletContext();



    public static void createBeanAndAddServletContext(Class c, Method md, String url, HttpMethod requestMethod) throws Exception {
        RequestUrlBean requestUrlBean=new RequestUrlBean();
        url=StringUtils.formatUrl(url);
        String[] urlArray=url.split("\\/");
        requestUrlBean.setRequestUrl(urlArray);
        requestUrlBean.setHandlerMethod(md);
        requestUrlBean.setHandler(c);
        String requestUrl = newUrl(requestMethod.name(),urlArray);
        requestUrlBean.setPathRequest(requestUrl.contains("*"));
        requestUrlBean.setHttpMethod(requestMethod);
        putServletContext(requestUrl,requestUrlBean);
    }

    private synchronized static String newUrl(String requestMethod,String[] urlArray){
        String url =requestMethod +":";
        for (String u:urlArray){
            if (u.indexOf("{")>=0 && u.indexOf("}")>=0){
                url+="/"+"*";
                continue;
            }
            url+= "/"+u;
        }
        return url;
    }

    private static void putServletContext(String contextUrl,RequestUrlBean bean) throws ServletException, ContextException {
        servletContext.registerBean(contextUrl,bean);
    }

}
