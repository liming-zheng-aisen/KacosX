package com.duanya.spring.framework.mvc.dispatcher;

import com.duanya.spring.commont.util.StringUtils;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import com.duanya.spring.framework.mvc.context.DyServletContext;
import com.duanya.spring.framework.mvc.context.exception.DyServletException;
import com.duanya.spring.framework.mvc.dispatcher.staticresouce.WebstaticResources;
import com.duanya.spring.framework.mvc.enums.DyMethod;
import com.duanya.spring.framework.mvc.handler.bean.RequestUrlBean;
import com.duanya.spring.framework.mvc.handler.impl.DyHandlerExecution;
import com.duanya.spring.framework.mvc.handler.mapping.HandlerMapping;
import com.duanya.spring.framework.mvc.result.ResultData;
import com.duanya.spring.framework.mvc.util.JsonUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 请求分发器
 */
public class DyDispatchedServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static String config;

    private static Class main=null;
    public DyDispatchedServlet() {

    }

    public DyDispatchedServlet(String conf,Class main) {
        if (StringUtils.isNotEmptyPlus(conf)) {
            config = conf;
        }
        if (null!=main) {
            DyDispatchedServlet.main = main;
        }
    }

    @Override
    public void init(ServletConfig servletConfig) {

        if (null == config) {
            config = servletConfig.getInitParameter("contextConfigLocation");
        }
        try {
            DyServletContext.load();
        } catch (DyServletException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, DyMethod.GET);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, DyMethod.HEAD);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, DyMethod.POST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, DyMethod.PUT);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, DyMethod.DELETE);

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, DyMethod.OPTIONS);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, DyMethod.TRACE);
    }

    private void doDispatched(HttpServletRequest req, HttpServletResponse resp, DyMethod method) throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setHeader("content-type", "text/html;charset=utf-8");

        if (req.getRequestURI().indexOf(".") > 0) {
            WebstaticResources webstaticResources=new WebstaticResources(DyConfigurationLoader.getEvn());
            if (webstaticResources.isStaticResourcesRequest(req.getRequestURI())){
                webstaticResources.doResource(req,resp);
                return;
            }
        }

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            HandlerMapping handlerMapping = new HandlerMapping();

            RequestUrlBean bean = handlerMapping.requestMethod(req.getRequestURI(), method);

            if (null == bean) {
                String result = JsonUtil.objectToJson(new ResultData<Object>(404, null, " NOT FOUND "));
                out.print(result);
                return;
            }
            DyHandlerExecution exec = new DyHandlerExecution();

            Object data = exec.handle(req, resp, bean);
            if (isBaseType(data.getClass(), true)) {
                out.print(data);
            } else {
                out.print(JsonUtil.objectToJson(data));
            }

        } catch (Exception e) {
            e.printStackTrace();
            String result = JsonUtil.objectToJson(new ResultData<Object>(502, e.getMessage(), " SERVER ERROR "));
            out.print(result);
        } finally {
            out.close();
        }
    }

    /**
     * 判断对象属性是否是基本数据类型,包括是否包括string
     *
     * @param className
     * @param incString 是否包括string判断,如果为true就认为string也是基本数据类型
     * @return
     */
    public static boolean isBaseType(Class className, boolean incString) {
        if (incString && className.equals(String.class)) {
            return true;
        }
        return className.equals(Integer.class) ||
                className.equals(int.class) ||
                className.equals(Byte.class) ||
                className.equals(byte.class) ||
                className.equals(Long.class) ||
                className.equals(long.class) ||
                className.equals(Double.class) ||
                className.equals(double.class) ||
                className.equals(Float.class) ||
                className.equals(float.class) ||
                className.equals(Character.class) ||
                className.equals(char.class) ||
                className.equals(Short.class) ||
                className.equals(short.class) ||
                className.equals(Boolean.class) ||
                className.equals(boolean.class);
    }

}
