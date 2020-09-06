package com.mx.framework.mvc.handler.adapter;


import com.mx.framework.mvc.handler.bean.RequestUrlBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description
 */
public interface HandlerAdapter {

    Object handle(HttpServletRequest request, HttpServletResponse response, RequestUrlBean handler) throws Exception;

}
