package com.mx.framework.mvc.context;

import com.mx.aop.factory.ProxyFacotry;
import com.mx.framework.context.base.ApplicationContextApi;
import com.mx.framework.context.exception.ContextException;
import com.mx.framework.mvc.handler.bean.RequestUrlBean;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description mvc上下文
 */

@Slf4j
public class ServletContext implements ApplicationContextApi {

    /**
     * String是由路由+请求方式
     */
    private static  Map<String, RequestUrlBean> servletContext = new HashMap<>();

    @Override
    public Object getBean(String beanName, Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        if (servletContext.containsKey(beanName)) {
            return servletContext.get(beanName);
        } else {
            return getBean(beanName);
        }

    }

    public Object getBean(String beanName) {
        String[] mu = beanName.split("\\:");
        if (mu.length < 2) {
            return null;
        }
        String[] urls = mu[1].split("\\/");
        for (RequestUrlBean urlBean : servletContext.values()) {
            if (urls.length != urlBean.getRequestUrl().length ||
                    !urlBean.getHttpMethod().name().equals(mu[0])) {
                continue;
            }
            if (difData(urls, urlBean.getRequestUrl())) {
                return urlBean;
            }
        }
        return null;
    }

    private boolean difData(String[] target, String[] source) {
        if (target.length != source.length) {
            return false;
        }
        for (int i = 0; i < target.length; i++) {
            if (source[i].contains("{") && source[i].contains("}")) {
                continue;
            }
            if (!source[i].equals(target[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean registerBean(String beanName, Object object) throws ContextException {
        if (servletContext.containsKey(beanName)) {
            throw new ContextException(beanName + "命名重复");
        }
        if (!(object instanceof RequestUrlBean)) {
            throw new ContextException("mvc上下文不接受" + object.getClass() + "的类型！");
        }

        RequestUrlBean requestUrlBean = (RequestUrlBean) object;
        servletContext.put(beanName, requestUrlBean);

        return true;
    }

    @Override
    public Object registerBean(String beanName, Class object) throws ContextException {
        if (servletContext.containsKey(beanName)) {
            throw new ContextException(beanName + "命名重复");
        }

        RequestUrlBean requestUrlBean = (RequestUrlBean) ProxyFacotry.getProxyInstance(object);
        servletContext.put(beanName, requestUrlBean);

        return requestUrlBean;
    }

    public static Map<String,RequestUrlBean> getServletContext(){
        return servletContext;
    }


    private ServletContext(){

    }

    public static class Builder{

        private final static ServletContext SERVLET_CONTEXT =new ServletContext();

        public static ServletContext getServletContext(){
            return SERVLET_CONTEXT;
        }
    }


}
