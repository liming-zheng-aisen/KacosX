package com.mx.framework.mvc.dispatcher;


import com.mx.common.http.HttpResponsePrintln;
import com.mx.common.http.result.ResultData;
import com.mx.common.util.JsonUtil;
import com.mx.common.util.TypeUtil;
import com.mx.framework.enums.HttpMethod;
import com.mx.framework.mvc.handler.bean.RequestUrlBean;
import com.mx.framework.mvc.handler.impl.HandlerExecution;
import com.mx.framework.mvc.handler.mapping.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author zheng.liming
 * @date 2019/8/4
 * @description 请求分发器
 */
public class DispatchedServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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

            RequestUrlBean bean = handlerMapping.requestMethod(req.getRequestURI(), method);


            if (null == bean) {
                String result = JsonUtil.objectToJson(new ResultData<Object>(404, null, " NOT FOUND "));
                HttpResponsePrintln.writer(resp,result);
                return;
            }

            HandlerExecution exec = new HandlerExecution();

            Object data = exec.handle(req, resp, bean);

            if (data==null){
                return;
            }

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

}
