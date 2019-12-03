package com.macos.framework.druid;

import com.alibaba.druid.stat.DruidStatService;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.macos.framework.annotation.Value;
import com.macos.framework.annotation.WebServlet;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc DruidServlet
 * @Author Zheng.LiMing
 * @Date 2019/9/13
 */
@WebServlet(url = "/druid/*")
public class DruidServlet extends ResourceServlet {
    private static final Log LOG = LogFactory.getLog(StatViewServlet.class);
    private static final long serialVersionUID = 1L;
    public static final String PARAM_NAME_RESET_ENABLE = "resetEnable";
    public static final String PARAM_NAME_JMX_URL = "jmxUrl";
    public static final String PARAM_NAME_JMX_USERNAME = "jmxUsername";
    public static final String PARAM_NAME_JMX_PASSWORD = "jmxPassword";
    private DruidStatService statService = DruidStatService.getInstance();
    private String jmxUrl = null;
    private String jmxUsername = null;
    private String jmxPassword = null;

    @Value("${dy.druid.userName}:dyboot")
    private String dyUserName="dyboot";

    @Value("${dy.druid.pwd}:123456")
    private String pwd="123456";

    private MBeanServerConnection conn = null;
    private ServletConfig config2;

    @Value("${dy.druid.resetEnable}:true")
    private Boolean resetEnable;

    public DruidServlet() {
        super("support/http/resources");
    }



    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.username=this.dyUserName;
        super.password=this.pwd;
        this.statService.setResetEnable(resetEnable);
        super.service(request, response);
    }

    private void initJmxConn() throws IOException {
        if (this.jmxUrl != null) {
            JMXServiceURL url = new JMXServiceURL(this.jmxUrl);
            Map<String, String[]> env = null;
            if (this.jmxUsername != null) {
                env = new HashMap();
                String[] credentials = new String[]{this.jmxUsername, this.jmxPassword};
                env.put("jmx.remote.credentials", credentials);
            }

            JMXConnector jmxc = JMXConnectorFactory.connect(url, env);
            this.conn = jmxc.getMBeanServerConnection();
        }

    }

    private String getJmxResult(MBeanServerConnection connetion, String url) throws Exception {
        ObjectName name = new ObjectName("com.alibaba.druid:type=DruidStatService");
        String result = (String)this.conn.invoke(name, "service", new String[]{url}, new String[]{String.class.getName()});
        return result;
    }

    @Override
    protected String process(String url) {
        String resp = null;
        if (this.jmxUrl == null) {
            resp = this.statService.service(url);
        } else if (this.conn == null) {
            try {
                this.initJmxConn();
            } catch (IOException var6) {
                LOG.error("init jmx connection error", var6);
                resp = DruidStatService.returnJSONResult(-1, "init jmx connection error" + var6.getMessage());
            }

            if (this.conn != null) {
                try {
                    resp = this.getJmxResult(this.conn, url);
                } catch (Exception var5) {
                    LOG.error("get jmx data error", var5);
                    resp = DruidStatService.returnJSONResult(-1, "get data error:" + var5.getMessage());
                }
            }
        } else {
            try {
                resp = this.getJmxResult(this.conn, url);
            } catch (Exception var4) {
                LOG.error("get jmx data error", var4);
                resp = DruidStatService.returnJSONResult(-1, "get data error" + var4.getMessage());
            }
        }

        return resp;
    }
    @Override
    public javax.servlet.ServletConfig getServletConfig() {
        return this.config2;
    }
}
