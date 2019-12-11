package com.macos.framework.mvc.context;

import com.duanya.spring.framework.mvc.handler.bean.RequestUrlBean;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.*;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.exception.ContextException;
import com.macos.framework.context.manager.ContextManager;
import com.macos.framework.mvc.context.exception.ServletException;
import com.macos.framework.mvc.enums.HttpMethod;

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

    /**
     * String是由路由+请求方式
     */
    private static volatile Map<String,RequestUrlBean> servletContext=new HashMap<>();

    @Override
    public Object getBean(String beanName,Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (servletContext.containsKey(beanName)){
            return servletContext.get(beanName);
        }else {
            return null;
        }

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

    private static void  addServletContextBean(Class c,String baseUrl){

        Method[] methods=c.getDeclaredMethods();

        for (Method md:methods){
            String requestUrl=baseUrl;
            String requestMethod=null;
            if (md.isAnnotationPresent(Get.class)){
                Get get=md.getDeclaredAnnotation(Get.class);
                requestUrl+=get.value();
                requestMethod= HttpMethod.GET.name();

            }else if (md.isAnnotationPresent(Post.class)){
                Post post=md.getDeclaredAnnotation(Post.class);
                requestUrl+=post.value();
                requestMethod= HttpMethod.POST.name();
            }else if (md.isAnnotationPresent(Delete.class)){
                Delete delete=md.getDeclaredAnnotation(Delete.class);
                requestUrl+=delete.value();
                requestMethod= HttpMethod.DELETE.name();
            }else if (md.isAnnotationPresent(Put.class)){
                Put put=md.getDeclaredAnnotation(Put.class);
                requestUrl+=put.value();
                requestMethod= HttpMethod.PUT.name();
            }else if (md.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMapping=md.getDeclaredAnnotation(RequestMapping.class);
                requestUrl+=requestMapping.value();
                requestMethod = requestMapping.method().name();
            }

            if (StringUtils.isNotEmptyPlus(requestMethod)) {
                createBeanAndAddServletContext(c, md,requestUrl,requestMethod);
            }

        }

    }

    private static void createBeanAndAddServletContext(Class c,Method md,String url,String requestMethod){
        RequestUrlBean requestUrlBean=new RequestUrlBean();
        url=StringUtils.formatUrl(url);
        //附带/{}的处理
        String regex=".*\\/\\{.*\\}.*";
        if (url.matches(regex)){
            requestUrlBean.setBringParam(true);
            int startIndex=url.lastIndexOf("/{");
            int endIndex=url.lastIndexOf("}");
            String paramName = url.substring(startIndex+2,endIndex);
            url=url.substring(0,startIndex+1);
            url+="*";
            requestUrlBean.setParamName(paramName);
            requestUrlBean.setBringParam(true);
        }
        requestUrlBean.setRequestUrl(url);
        url+=requestMethod;
        requestUrlBean.setMethod(md);
        requestUrlBean.setHandler(c);
        putServletContext(url,requestUrlBean);
    }

    public static void putServletContext(String contextUrl,RequestUrlBean bean){
        servletContext.put(contextUrl,bean);
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
