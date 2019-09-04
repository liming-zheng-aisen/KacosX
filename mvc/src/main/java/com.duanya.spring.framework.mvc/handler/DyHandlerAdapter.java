package com.duanya.spring.framework.mvc.handler;

import com.duanya.spring.framework.mvc.handler.bean.RequestUrlBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description
 */
public interface DyHandlerAdapter {

    Object handle(HttpServletRequest request, HttpServletResponse response, RequestUrlBean handler, Properties env) throws Exception;

}
