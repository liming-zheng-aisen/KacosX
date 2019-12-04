package com.macos.framework.mvc.dispatcher;


import com.macos.common.http.HttpResponsePrintln;
import com.macos.common.http.result.ResultData;
import com.macos.common.properties.LoadPropeties;
import com.macos.common.scanner.api.ScannerApi;
import com.macos.common.scanner.impl.ScannerImpl;
import com.macos.common.util.JsonUtil;
import com.macos.common.util.StringUtils;
import com.macos.common.util.TypeUtil;
import com.macos.framework.core.bean.BeanManager;
import com.macos.framework.core.load.ConfigurationLoader;
import com.macos.framework.mvc.context.ServletContext;
import com.macos.framework.mvc.enums.HttpMethod;
import com.macos.framework.mvc.handler.impl.HandlerExecution;
import com.macos.framework.mvc.handler.mapping.HandlerMapping;

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
public class DispatchedServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static String DEFAULT_SCANNER_KEY="dy.mvc.scan";

    private static String config;

    private static Set<Class> cl;


    public DispatchedServlet() {
        cl= BeanManager.getClassContainer();
        try {

            Properties evn= ConfigurationLoader.getEvn();

            if (null == evn) {
                evn = LoadPropeties.doLoadProperties(null, config, this.getClass());
            }

            if (cl == null && BeanManager.isLoad()) {
                cl = BeanManager.getClassContainer();
            }

            if (cl == null) {
                if (null != evn) {
                    String path = evn.getProperty(DEFAULT_SCANNER_KEY);
                    if (StringUtils.isNotEmptyPlus(path)) {
                        ScannerApi scanner = new ScannerImpl();
                        cl = scanner.doScanner(path);
                    } else {
                        throw new Exception("请在dy-properties配置dy.mvc.scan（mvc根路径）的值");
                    }
                }
            }

            ServletContext.load(cl);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, HttpMethod.GET);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, HttpMethod.HEAD);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, HttpMethod.POST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, HttpMethod.PUT);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, HttpMethod.DELETE);

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, HttpMethod.OPTIONS);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatched(req, resp, HttpMethod.TRACE);
    }

    private void doDispatched(HttpServletRequest req, HttpServletResponse resp, HttpMethod method) throws IOException, ServletException {

        setDefaultEncoding(req,resp);

        try {

            HandlerMapping handlerMapping = new HandlerMapping();

            com.duanya.spring.framework.mvc.handler.bean.RequestUrlBean bean = handlerMapping.requestMethod(req.getRequestURI(), method);


            if (null == bean) {
                String result = JsonUtil.objectToJson(new ResultData<Object>(404, null, " NOT FOUND "));
                HttpResponsePrintln.writer(resp,result);
                return;
            }

            HandlerExecution exec = new HandlerExecution();

            Object data = exec.handle(req, resp, bean);

            if (TypeUtil.isBaseType(data.getClass(), true)) {
                resp.setContentType("text/html;charset=UTF-8");
                HttpResponsePrintln.writer(resp,data);
            } else {
                resp.setContentType("application/json;charset=UTF-8");
                HttpResponsePrintln.writer(resp,JsonUtil.objectToJson(data));
            }

        } catch (Exception e) {
            e.printStackTrace();
            String result = JsonUtil.objectToJson(new ResultData<Object>(502, e.getMessage(), " SERVER ERROR "));
            HttpResponsePrintln.writer(resp,result);
        }

    }

    private  void  setDefaultEncoding(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        resp.setHeader("content-type", "text/html;charset=utf-8");
    }


    public static Set<Class> getCl() {
        return cl;
    }

    public static void setCl(Set<Class> cl) {
        DispatchedServlet.cl = cl;
    }
}
