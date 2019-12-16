package com.macos.framework.mvc.context;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.*;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.exception.ContextException;
import com.macos.framework.context.manager.ContextManager;
import com.macos.framework.mvc.context.exception.ServletException;
import com.macos.framework.mvc.enums.HttpMethod;
import com.macos.framework.mvc.handler.bean.RequestUrlBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description mvc上下文
 */
public class ServletContext implements ApplicationContextApi {

    private static final Logger log = LoggerFactory.getLogger(ServletContext.class);

    /**
     * String是由路由+请求方式
     */
    private static volatile Map<String, RequestUrlBean> servletContext=new HashMap<>();

    @Override
    public Object getBean(String beanName,Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        if (servletContext.containsKey(beanName)){
            return servletContext.get(beanName);
        }else {
            return getBean(beanName);
        }

    }

    public Object getBean(String beanName){
        String[] mu = beanName.split("\\:");
        if (mu.length<2){
            return null;
        }
        String[] urls =mu[1].split("\\/");
        for (RequestUrlBean urlBean : servletContext.values()){
            if (urls.length != urlBean.getRequestUrl().length ||
                    !urlBean.getHttpMethod().name().equals(mu[0])){
                continue;
            }
            if (difData(urls,urlBean.getRequestUrl())){
                return urlBean;
            }
        }
        return null;
    }

    private boolean difData(String[] target,String[] source){
        if (target.length!=source.length){
            return false;
        }
        for (int i=0;i<target.length;i++){
            if (source[i].contains("{") && source[i].contains("}")){
                continue;
            }
            if (!source[i].equals(target[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public void registerBean(String beanName, Object object) throws ContextException {
        if (servletContext.containsKey(beanName)) {
            throw  new ContextException(beanName+"命名重复");
        }
        if (!(object instanceof RequestUrlBean)){
            throw  new ContextException("mvc上下文不接受"+object.getClass()+"的类型！");
        }
        servletContext.put(beanName, (RequestUrlBean)object);
    }

    public static void load(Set<Class> clazzList) throws ServletException {


      if (clazzList==null){
        throw new ServletException("DyServletContext容器加载失败，请检查是扫描路径是否正确！");
      }

      for (Class c:clazzList){
          if (c.isAnnotationPresent(RestAPI.class)){
               String baseUrl="/";
               if (c.isAnnotationPresent(RequestMapping.class)){
                   RequestMapping dyRequestMapping= (RequestMapping) c.getAnnotation(RequestMapping.class);
                   baseUrl=dyRequestMapping.value();
                   addServletContextBean(c,baseUrl);
               }else {
                   addServletContextBean(c,baseUrl);
               }
          }
      }
    }

    private static void  addServletContextBean(Class c,String baseUrl) throws ServletException {

        Method[] methods=c.getDeclaredMethods();

        for (Method md:methods){
            String requestUrl=baseUrl;
            HttpMethod requestMethod=null;
            if (md.isAnnotationPresent(Get.class)){
                Get get=md.getDeclaredAnnotation(Get.class);
                requestUrl+=get.value();
                requestMethod= HttpMethod.GET;
            }else if (md.isAnnotationPresent(Post.class)){
                Post post=md.getDeclaredAnnotation(Post.class);
                requestUrl+=post.value();
                requestMethod= HttpMethod.POST;
            }else if (md.isAnnotationPresent(Delete.class)){
                Delete delete=md.getDeclaredAnnotation(Delete.class);
                requestUrl+=delete.value();
                requestMethod= HttpMethod.DELETE;
            }else if (md.isAnnotationPresent(Put.class)){
                Put put=md.getDeclaredAnnotation(Put.class);
                requestUrl+=put.value();
                requestMethod= HttpMethod.PUT;
            }else if (md.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMapping=md.getDeclaredAnnotation(RequestMapping.class);
                requestUrl+=requestMapping.value();
                requestMethod = requestMapping.method();
            }
            if (requestMethod != null) {
                createBeanAndAddServletContext(c, md,requestUrl,requestMethod);
            }
        }
    }

    public synchronized static String newUrl(String requestMethod,String[] urlArray){
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

    private static void createBeanAndAddServletContext(Class c,Method md,String url,HttpMethod requestMethod) throws ServletException {
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

    public static void putServletContext(String contextUrl,RequestUrlBean bean) throws ServletException {
        if (servletContext.containsKey(contextUrl)) {
            throw new ServletException("请求路径"+contextUrl+"重复！");
        }
        servletContext.put(contextUrl, bean);
    }

    public static Map<String,RequestUrlBean> getServletContext(){
        return servletContext;
    }


    private ServletContext(){

    }

    public static class Builder{

        private final static ServletContext SERVLET_CONTEXT =new ServletContext();

        public static ServletContext getServletContext(){
            ContextManager contextManager = ContextManager.BuilderContext.getContextManager();
            if (!contextManager.hasContext(SERVLET_CONTEXT)){
                contextManager.registerApplicationContext(SERVLET_CONTEXT);
            }
            return SERVLET_CONTEXT;
        }
    }


}
