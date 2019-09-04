package com.duanya.start.web.tomcat;

import com.duanya.spring.common.times.DyTimer;
import com.duanya.spring.framework.context.base.DyApplicationContext;
import com.duanya.spring.framework.core.listener.api.IDyLoadListener;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc TomcatStarterListener
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class TomcatStarterListener implements IDyLoadListener {

   private final static Logger log=LoggerFactory.getLogger(TomcatStarterListener.class);

    @Override
    public void notice(DyApplicationContext context) {
        try {
            Tomcat tomcat=(Tomcat) context.getBean("tomcat");

            DyTimer dyTimer=(DyTimer) context.getBean("dyTimer");
            // 启动tomcat
            tomcat.start();
            log.info("Dyboot starter web 成功启动，花费时间为{}ms",dyTimer.spendingTime());
            // 保持tomcat的启动状态
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
