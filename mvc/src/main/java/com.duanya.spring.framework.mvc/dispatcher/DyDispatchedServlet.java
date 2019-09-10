package com.duanya.spring.framework.mvc.dispatcher;

import com.duanya.spring.common.http.HttpResponsePrintln;
import com.duanya.spring.common.http.result.ResultData;
import com.duanya.spring.common.properties.DyLoadPropeties;
import com.duanya.spring.common.scanner.api.IDyScanner;
import com.duanya.spring.common.scanner.impl.DyScannerImpl;
import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.core.bean.factory.bean.manager.DyBeanManager;
import com.duanya.spring.framework.mvc.context.DyServletContext;
import com.duanya.spring.framework.mvc.enums.DyMethod;
import com.duanya.spring.framework.mvc.handler.bean.RequestUrlBean;
import com.duanya.spring.framework.mvc.handler.impl.DyHandlerExecution;
import com.duanya.spring.framework.mvc.handler.mapping.HandlerMapping;
import com.duanya.spring.framework.mvc.util.JsonUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Set;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 请求分发器
 */
public class DyDispatchedServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static String DEFAULT_SCANNER_KEY="dy.mvc.scan";

    private static String config;

    private static Properties evn;

    private static Set<Class> cl;


    public DyDispatchedServlet() {

    }

    public DyDispatchedServlet(Properties properties,Set<Class> classes) {
        evn=properties;
        cl=classes;
    }

    @Override
    public void init(ServletConfig servletConfig) {

        if (null == config) {
            config = servletConfig.getInitParameter("contextConfigLocation");
        }

        try {

            if (null == evn) {
                evn = DyLoadPropeties.doLoadProperties(null, config, this.getClass());
            }

            if (cl == null && DyBeanManager.isLoad()) {
                cl = DyBeanManager.getClassContainer();
            }

            if (cl == null) {
                if (null != evn) {
                    String path = evn.getProperty(DEFAULT_SCANNER_KEY);
                    if (StringUtils.isNotEmptyPlus(path)) {
                        IDyScanner scanner = new DyScannerImpl();
                        cl = scanner.doScanner(path);
                    } else {
                        throw new Exception("请在dy-properties配置dy.mvc.scan（mvc根路径）的值");
                    }
                }
            }

            DyServletContext.load(cl);

        } catch (Exception e) {
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

    private void doDispatched(HttpServletRequest req, HttpServletResponse resp, DyMethod method) throws IOException, ServletException {

        setDefaultEncoding(req,resp);

        try {
            Properties  evn = DyDispatchedServlet.getEvn();

            HandlerMapping handlerMapping = new HandlerMapping();

            RequestUrlBean bean = handlerMapping.requestMethod(req.getRequestURI(), method);


            if (null == bean) {
                String result = JsonUtil.objectToJson(new ResultData<Object>(404, null, " NOT FOUND "));
                HttpResponsePrintln.writer(resp,result);
                return;
            }

            DyHandlerExecution exec = new DyHandlerExecution();

            Object data = exec.handle(req, resp, bean);

            if (isBaseType(data.getClass(), true)) {
                HttpResponsePrintln.writer(resp,data);
            } else {
                HttpResponsePrintln.writer(resp,JsonUtil.objectToJson(data));
            }

        } catch (Exception e) {
            e.printStackTrace();
            String result = JsonUtil.objectToJson(new ResultData<Object>(502, e.getMessage(), " SERVER ERROR "));
            HttpResponsePrintln.writer(resp,result);
        }

    }

    /**
     * 判断对象属性是否是基本数据类型,包括是否包括string
     *
     * @param className
     * @param incString 是否包括string判断,如果为true就认为string也是基本数据类型
     * @return
     */
    private static boolean isBaseType(Class className, boolean incString) {
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
    private  void  setDefaultEncoding(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setHeader("content-type", "text/html;charset=utf-8");
        resp.setContentType("application/json;charset=UTF-8");
    }

    public static Properties getEvn() {
        return evn;
    }

    public static Set<Class> getCl() {
        return cl;
    }

    public static void setCl(Set<Class> cl) {
        DyDispatchedServlet.cl = cl;
    }
}
