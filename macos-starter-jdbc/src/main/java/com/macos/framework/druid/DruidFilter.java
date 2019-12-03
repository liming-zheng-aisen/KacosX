package com.macos.framework.druid;

import com.alibaba.druid.filter.stat.StatFilterContext;
import com.alibaba.druid.support.http.AbstractWebStatImpl;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.http.stat.*;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.druid.support.profile.ProfileEntryKey;
import com.alibaba.druid.support.profile.ProfileEntryReqStat;
import com.alibaba.druid.support.profile.Profiler;
import com.alibaba.druid.util.DruidWebUtils;
import com.alibaba.druid.util.PatternMatcher;
import com.alibaba.druid.util.ServletPathMatcher;
import com.duanya.spring.framework.annotation.DyOrder;
import com.duanya.spring.framework.annotation.DyValue;
import com.duanya.spring.framework.annotation.DyWebFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.*;

/**
 * @Desc DruidFilter
 * @Author Zheng.LiMing
 * @Date 2019/9/13
 */
@DyWebFilter
@DyOrder(1)
@SuppressWarnings("all")
public class DruidFilter extends AbstractWebStatImpl implements Filter {
    private static final Log LOG = LogFactory.getLog(WebStatFilter.class);
    public static final String PARAM_NAME_PROFILE_ENABLE = "profileEnable";
    public static final String PARAM_NAME_SESSION_STAT_ENABLE = "sessionStatEnable";
    public static final String PARAM_NAME_SESSION_STAT_MAX_COUNT = "sessionStatMaxCount";
    public static final String PARAM_NAME_EXCLUSIONS = "exclusions";
    public static final String PARAM_NAME_PRINCIPAL_SESSION_NAME = "principalSessionName";
    public static final String PARAM_NAME_PRINCIPAL_COOKIE_NAME = "principalCookieName";
    public static final String PARAM_NAME_REAL_IP_HEADER = "realIpHeader";

    @DyValue("${dy.druid.exclusions}:*.js,*.gif,*.jpg,*.png,*.css,*.ico,*.jsp,/druid/*,/download/*")
    private String exclusions;

    @DyValue("${dy.druid.sessionStatMaxCount}:2000")
    private String sessionStatMaxCount2;

    @DyValue("${dy.druid.sessionStatEnable}:true")
    private String sessionStatEnable2;


    @DyValue("${dy.druid.principalSessionName}:session_user_key")
    private String principalSessionName2;

    @DyValue("${dy.druid.profileEnable}:true")
    private String profileEnable2;



    protected PatternMatcher pathMatcher = new ServletPathMatcher();
    private Set<String> excludesPattern;

    public DruidFilter() {
        super.webAppStat=new WebAppStat();
    }



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        WebStatFilter.StatHttpServletResponseWrapper responseWrapper = new WebStatFilter.StatHttpServletResponseWrapper(httpResponse);
        String requestURI = this.getRequestURI(httpRequest);
        if (this.isExclusion(requestURI)) {
            chain.doFilter(request, response);
        } else {
            long startNano = System.nanoTime();
            long startMillis = System.currentTimeMillis();
            WebRequestStat requestStat = new WebRequestStat(startNano, startMillis);
            WebRequestStat.set(requestStat);
            WebSessionStat sessionStat = this.getSessionStat(httpRequest);
            this.webAppStat.beforeInvoke();
            WebURIStat uriStat = this.webAppStat.getURIStat(requestURI, false);
            if (uriStat == null) {
                int index = requestURI.indexOf(";jsessionid=");
                if (index != -1) {
                    requestURI = requestURI.substring(0, index);
                    uriStat = this.webAppStat.getURIStat(requestURI, false);
                }
            }

            if (this.isProfileEnable()) {
                Profiler.initLocal();
                Profiler.enter(requestURI, "WEB");
            }

            if (uriStat != null) {
                uriStat.beforeInvoke();
            }

            if (sessionStat != null) {
                sessionStat.beforeInvoke();
            }

            Object error = null;
            boolean var34 = false;

            try {
                var34 = true;
                chain.doFilter(request, responseWrapper);
                var34 = false;
            } catch (IOException var35) {
                error = var35;
                throw var35;
            } catch (ServletException var36) {
                error = var36;
                throw var36;
            } catch (RuntimeException var37) {
                error = var37;
                throw var37;
            } catch (Error var38) {
                error = var38;
                throw var38;
            } finally {
                if (var34) {
                    long endNano = System.nanoTime();
                    requestStat.setEndNano(endNano);
                    long nanos = endNano - startNano;
                    this.webAppStat.afterInvoke((Throwable)error, nanos);
                    if (sessionStat == null) {
                        sessionStat = this.getSessionStat(httpRequest);
                        if (sessionStat != null) {
                            sessionStat.beforeInvoke();
                        }
                    }

                    if (sessionStat != null) {
                        sessionStat.afterInvoke((Throwable)error, nanos);
                        sessionStat.setPrincipal(this.getPrincipal(httpRequest));
                    }

                    if (uriStat == null) {
                        int status = responseWrapper.getStatus();
                        if (status == 404) {
                            String errorUrl = this.contextPath + "error_" + status;
                            uriStat = this.webAppStat.getURIStat(errorUrl, true);
                        } else {
                            uriStat = this.webAppStat.getURIStat(requestURI, true);
                        }

                        if (uriStat != null) {
                            uriStat.beforeInvoke();
                        }
                    }

                    if (uriStat != null) {
                        uriStat.afterInvoke((Throwable)error, nanos);
                    }

                    WebRequestStat.set((WebRequestStat)null);
                    if (this.isProfileEnable()) {
                        Profiler.release(nanos);
                        Map<ProfileEntryKey, ProfileEntryReqStat> requestStatsMap = Profiler.getStatsMap();
                        if (uriStat != null) {
                            uriStat.getProfiletat().record(requestStatsMap);
                        }

                        Profiler.removeLocal();
                    }

                }
            }

            long endNano = System.nanoTime();
            requestStat.setEndNano(endNano);
            long nanos = endNano - startNano;
            this.webAppStat.afterInvoke((Throwable)error, nanos);
            if (sessionStat == null) {
                sessionStat = this.getSessionStat(httpRequest);
                if (sessionStat != null) {
                    sessionStat.beforeInvoke();
                }
            }

            if (sessionStat != null) {
                sessionStat.afterInvoke((Throwable)error, nanos);
                sessionStat.setPrincipal(this.getPrincipal(httpRequest));
            }

            if (uriStat == null) {
                int status = responseWrapper.getStatus();
                if (status == 404) {
                    String errorUrl = this.contextPath + "error_" + status;
                    uriStat = this.webAppStat.getURIStat(errorUrl, true);
                } else {
                    uriStat = this.webAppStat.getURIStat(requestURI, true);
                }

                if (uriStat != null) {
                    uriStat.beforeInvoke();
                }
            }

            if (uriStat != null) {
                uriStat.afterInvoke((Throwable)error, nanos);
            }

            WebRequestStat.set((WebRequestStat)null);
            if (this.isProfileEnable()) {
                Profiler.release(nanos);
                Map<ProfileEntryKey, ProfileEntryReqStat> requestStatsMap = Profiler.getStatsMap();
                if (uriStat != null) {
                    uriStat.getProfiletat().record(requestStatsMap);
                }

                Profiler.removeLocal();
            }

        }
    }

    public boolean isExclusion(String requestURI) {
        if (this.excludesPattern == null) {
            return false;
        } else {
            if (this.contextPath != null && requestURI.startsWith(this.contextPath)) {
                requestURI = requestURI.substring(this.contextPath.length());
                if (!requestURI.startsWith("/")) {
                    requestURI = "/" + requestURI;
                }
            }

            Iterator i$ = this.excludesPattern.iterator();

            String pattern;
            do {
                if (!i$.hasNext()) {
                    return false;
                }

                pattern = (String)i$.next();
            } while(!this.pathMatcher.matches(pattern, requestURI));

            return true;
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        String param = exclusions;
        if (param != null && param.trim().length() != 0) {
            this.excludesPattern = new HashSet(Arrays.asList(param.split("\\s*,\\s*")));
        }

        param = config.getInitParameter("principalSessionName");
        if (param != null) {
            param = param.trim();
            if (param.length() != 0) {
                this.principalSessionName = param;
            }
        }

        param = config.getInitParameter("principalCookieName");
        if (param != null) {
            param = param.trim();
            if (param.length() != 0) {
                this.principalCookieName = param;
            }
        }

        param = sessionStatEnable2;
        if (param != null && param.trim().length() != 0) {
            param = param.trim();
            if ("true".equals(param)) {
                this.sessionStatEnable = true;
            } else if ("false".equals(param)) {
                this.sessionStatEnable = false;
            } else {
                LOG.error("WebStatFilter Parameter 'sessionStatEnable' config error");
            }
        }

        param = config.getInitParameter("profileEnable");
        if (param != null && param.trim().length() != 0) {
            param = param.trim();
            if ("true".equals(param)) {
                this.profileEnable = true;
            } else if ("false".equals(param)) {
                this.profileEnable = false;
            } else {
                LOG.error("WebStatFilter Parameter 'profileEnable' config error");
            }
        }

        param = sessionStatMaxCount2;
        if (param != null && param.trim().length() != 0) {
            param = param.trim();

            try {
                this.sessionStatMaxCount = Integer.parseInt(param);
            } catch (NumberFormatException var4) {
                LOG.error("WebStatFilter Parameter 'sessionStatEnable' config error", var4);
            }
        }

        param = config.getInitParameter("realIpHeader");
        if (param != null) {
            param = param.trim();
            if (param.length() != 0) {
                this.realIpHeader = param;
            }
        }

        StatFilterContext.getInstance().addContextListener(this.statFilterContextListener);
        this.contextPath = DruidWebUtils.getContextPath(config.getServletContext());
        if (this.webAppStat == null) {
            this.webAppStat = new WebAppStat(this.contextPath, this.sessionStatMaxCount);
        }

        WebAppStatManager.getInstance().addWebAppStatSet(this.webAppStat);
    }

    @Override
    public void destroy() {
        StatFilterContext.getInstance().removeContextListener(this.statFilterContextListener);
        if (this.webAppStat != null) {
            WebAppStatManager.getInstance().remove(this.webAppStat);
        }

    }

    public void setWebAppStat(WebAppStat webAppStat) {
        this.webAppStat = webAppStat;
    }

    public WebAppStat getWebAppStat() {
        return this.webAppStat;
    }

    public WebStatFilterContextListener getStatFilterContextListener() {
        return this.statFilterContextListener;
    }

    public static final class StatHttpServletResponseWrapper extends HttpServletResponseWrapper implements HttpServletResponse {
        private int status = 200;

        public StatHttpServletResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void setStatus(int statusCode) {
            super.setStatus(statusCode);
            this.status = statusCode;
        }

        @Override
        public void setStatus(int statusCode, String statusMessage) {
            super.setStatus(statusCode, statusMessage);
            this.status = statusCode;
        }

        @Override
        public void sendError(int statusCode, String statusMessage) throws IOException {
            super.sendError(statusCode, statusMessage);
            this.status = statusCode;
        }

        @Override
        public void sendError(int statusCode) throws IOException {
            super.sendError(statusCode);
            this.status = statusCode;
        }

        @Override
        public int getStatus() {
            return this.status;
        }
    }
}
