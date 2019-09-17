package com.duanya.spring.framework.druid;

import com.alibaba.druid.support.http.ResourceServlet;
import com.alibaba.druid.support.http.util.IPAddress;
import com.alibaba.druid.support.http.util.IPRange;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.druid.util.Utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Desc DyResourceServlet
 * @Author Zheng.LiMing
 * @Date 2019/9/14
 */
public abstract class DyResourceServlet extends HttpServlet {
    private static final Log LOG = LogFactory.getLog(ResourceServlet.class);
    public static final String SESSION_USER_KEY = "druid-user";
    public static final String PARAM_NAME_USERNAME = "loginUsername";
    public static final String PARAM_NAME_PASSWORD = "loginPassword";
    public static final String PARAM_NAME_ALLOW = "allow";
    public static final String PARAM_NAME_DENY = "deny";
    public static final String PARAM_REMOTE_ADDR = "remoteAddress";
    protected String username = null;
    protected String password = null;
    protected List<IPRange> allowList = new ArrayList();
    protected List<IPRange> denyList = new ArrayList();
    protected final String resourcePath;
    protected String remoteAddressHeader = null;

    private ServletConfig config;

    public DyResourceServlet(String resourcePath) {
        this.resourcePath = resourcePath;

    }


    @Override
    public void init(ServletConfig config) throws ServletException {
    }

    private void initAuthEnv() {
        String paramRemoteAddressHeader = this.config.getInitParameter("remoteAddress");
        if (!StringUtils.isEmpty(paramRemoteAddressHeader)) {
            this.remoteAddressHeader = paramRemoteAddressHeader;
        }

        String param;
        String msg;
        String[] arr$;
        int len$;
        int i$;
        String item;
        IPRange ipRange;
        String[] items;
        try {
            param = this.config.getInitParameter("allow");
            if (param != null && param.trim().length() != 0) {
                param = param.trim();
                items = param.split(",");
                arr$ = items;
                len$ = items.length;

                for(i$ = 0; i$ < len$; ++i$) {
                    item = arr$[i$];
                    if (item != null && item.length() != 0) {
                        ipRange = new IPRange(item);
                        this.allowList.add(ipRange);
                    }
                }
            }
        } catch (Exception var12) {
            msg = "initParameter config error, allow : " + this.config.getInitParameter("allow");
            LOG.error(msg, var12);
        }

        try {
            param = this.config.getInitParameter("deny");
            if (param != null && param.trim().length() != 0) {
                param = param.trim();
                items = param.split(",");
                arr$ = items;
                len$ = items.length;

                for(i$ = 0; i$ < len$; ++i$) {
                    item = arr$[i$];
                    if (item != null && item.length() != 0) {
                        ipRange = new IPRange(item);
                        this.denyList.add(ipRange);
                    }
                }
            }
        } catch (Exception var11) {
            msg = "initParameter config error, deny : " + this.config.getInitParameter("deny");
            LOG.error(msg, var11);
        }

    }

    public boolean isPermittedRequest(String remoteAddress) {
        boolean ipV6 = remoteAddress != null && remoteAddress.indexOf(58) != -1;
        if (!ipV6) {
            IPAddress ipAddress = new IPAddress(remoteAddress);
            Iterator i$ = this.denyList.iterator();

            IPRange range;
            do {
                if (!i$.hasNext()) {
                    if (this.allowList.size() > 0) {
                        i$ = this.allowList.iterator();

                        do {
                            if (!i$.hasNext()) {
                                return false;
                            }

                            range = (IPRange)i$.next();
                        } while(!range.isIPAddressInRange(ipAddress));

                        return true;
                    }

                    return true;
                }

                range = (IPRange)i$.next();
            } while(!range.isIPAddressInRange(ipAddress));

            return false;
        } else {
            return "0:0:0:0:0:0:0:1".equals(remoteAddress) || this.denyList.size() == 0 && this.allowList.size() == 0;
        }
    }

    protected String getFilePath(String fileName) {
        return this.resourcePath + fileName;
    }

    protected void returnResourceFile(String fileName, String uri, HttpServletResponse response) throws ServletException, IOException {
        String filePath = this.getFilePath(fileName);
        if (fileName.endsWith(".jpg")) {
            byte[] bytes = Utils.readByteArrayFromResource(filePath);
            if (bytes != null) {
                response.getOutputStream().write(bytes);
            }

        } else {
            String text = Utils.readFromResource(filePath);
            if (text == null) {
                response.sendRedirect(uri + "/index.html");
            } else {
                if (fileName.endsWith(".css")) {
                    response.setContentType("text/css;charset=utf-8");
                } else if (fileName.endsWith(".js")) {
                    response.setContentType("text/javascript;charset=utf-8");
                }

                response.getWriter().write(text);
            }
        }
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();
        response.setCharacterEncoding("utf-8");
        if (contextPath == null) {
            contextPath = "";
        }

        String uri = contextPath + servletPath;
        String path = requestURI.substring(contextPath.length() + servletPath.length());
        if (!this.isPermittedRequest(request)) {
            path = "/nopermit.html";
            this.returnResourceFile(path, uri, response);
        } else {
            String fullUrl;
            if ("/submitLogin".equals(path)) {
                fullUrl = request.getParameter("loginUsername");
                String passwordParam = request.getParameter("loginPassword");
                if (this.username.equals(fullUrl) && this.password.equals(passwordParam)) {
                    request.getSession().setAttribute("druid-user", this.username);
                    response.getWriter().print("success");
                } else {
                    response.getWriter().print("error");
                }

            } else if (this.isRequireAuth() && !this.ContainsUser(request) && !"/login.html".equals(path) && !path.startsWith("/css") && !path.startsWith("/js") && !path.startsWith("/img")) {
                if (!contextPath.equals("") && !contextPath.equals("/")) {
                    if ("".equals(path)) {
                        response.sendRedirect("druid/login.html");
                    } else {
                        response.sendRedirect("login.html");
                    }
                } else {
                    response.sendRedirect("/druid/login.html");
                }

            } else if (!"".equals(path)) {
                if ("/".equals(path)) {
                    response.sendRedirect("index.html");
                } else if (path.contains(".json")) {
                    fullUrl = path;
                    if (request.getQueryString() != null && request.getQueryString().length() > 0) {
                        fullUrl = path + "?" + request.getQueryString();
                    }

                    response.getWriter().print(this.process(fullUrl));
                } else {
                    this.returnResourceFile(path, uri, response);
                }
            } else {
                if (!contextPath.equals("") && !contextPath.equals("/")) {
                    response.sendRedirect("druid/index.html");
                } else {
                    response.sendRedirect("/druid/index.html");
                }

            }
        }
    }

    public boolean ContainsUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("druid-user") != null;
    }

    public boolean isRequireAuth() {
        return this.username != null;
    }

    public boolean isPermittedRequest(HttpServletRequest request) {
        String remoteAddress = this.getRemoteAddress(request);
        return this.isPermittedRequest(remoteAddress);
    }

    protected String getRemoteAddress(HttpServletRequest request) {
        String remoteAddress = null;
        if (this.remoteAddressHeader != null) {
            remoteAddress = request.getHeader(this.remoteAddressHeader);
        }

        if (remoteAddress == null) {
            remoteAddress = request.getRemoteAddr();
        }

        return remoteAddress;
    }

    protected abstract String process(String var1);
}
