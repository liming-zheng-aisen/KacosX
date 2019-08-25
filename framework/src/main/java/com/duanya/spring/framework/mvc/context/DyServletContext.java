package com.duanya.spring.framework.mvc.context;

import com.duanya.spring.commont.util.StringUtils;
import com.duanya.spring.framework.context.base.DyApplicationContext;
import com.duanya.spring.framework.core.annotation.*;
import com.duanya.spring.framework.core.load.DyClassLoader;
import com.duanya.spring.framework.mvc.context.exception.DyServletException;
import com.duanya.spring.framework.mvc.enums.DyMethod;
import com.duanya.spring.framework.mvc.handler.bean.RequestUrlBean;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description mvc上下文
 */
public class DyServletContext extends DyApplicationContext {

    /**
     * String是由路由+请求方式
     */
    private static Map<String,RequestUrlBean> servletContext=new HashMap<>();

    public static void putServletContext(String contextUrl,RequestUrlBean bean){
        servletContext.put(contextUrl,bean);
    }

    public static Map<String,RequestUrlBean> getServletContext(){
        return servletContext;
    }

    public static void load() throws DyServletException {
      List<Class> clazzList= DyClassLoader.getClassContainer();

      if (clazzList==null){
        throw new DyServletException("DyServletContext容器加载失败，请检查是扫描路径是否正确！");
      }

      for (Class c:clazzList){
          if (c.isAnnotationPresent(DyRestController.class)){
               String baseUrl="";
               if (c.isAnnotationPresent(DyRequestMapping.class)){
                    DyRequestMapping dyRequestMapping= (DyRequestMapping) c.getAnnotation(DyRequestMapping.class);
                   baseUrl=dyRequestMapping.value();
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
            if (md.isAnnotationPresent(DyGet.class)){
                DyGet get=md.getDeclaredAnnotation(DyGet.class);
                requestUrl+=get.value();
                requestMethod= DyMethod.GET.name();

            }else if (md.isAnnotationPresent(DyPost.class)){
                DyPost post=md.getDeclaredAnnotation(DyPost.class);
                requestUrl+=post.value();
                requestMethod= DyMethod.POST.name();
            }else if (md.isAnnotationPresent(DyDelete.class)){
                DyDelete delete=md.getDeclaredAnnotation(DyDelete.class);
                requestUrl+=delete.value();
                requestMethod= DyMethod.DELETE.name();
            }else if (md.isAnnotationPresent(DyPut.class)){
                DyPut put=md.getDeclaredAnnotation(DyPut.class);
                requestUrl+=put.value();
                requestMethod= DyMethod.PUT.name();
            }else if (md.isAnnotationPresent(DyRequestMapping.class)){
                DyRequestMapping requestMapping=md.getDeclaredAnnotation(DyRequestMapping.class);
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
            url=url.substring(0,startIndex);
            requestUrlBean.setParamName(paramName);
            requestUrlBean.setBringParam(true);
        }
        url+=requestMethod;
        requestUrlBean.setMethod(md);
        requestUrlBean.setHandler(c);
        putServletContext(url,requestUrlBean);
    }

}
