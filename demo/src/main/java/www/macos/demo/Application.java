package www.macos.demo;

import com.mx.console.log.Logger;
import com.mx.console.log.factory.LoggerFactory;
import com.mx.console.log.impl.LogLevel;
import com.mx.framework.annotation.boot.MacosXApplication;
import com.mx.framework.starter.run.MacosXApplicationRun;


/**
 * @Desc Application
 * @Author liming.zheng
 * @Date 2020/2/25
 */
@MacosXApplication
public class Application {
    private final static Logger logger = LoggerFactory.buildLogger(Application.class);

    public static void main(String[] args) {
        MacosXApplicationRun.run(Application.class, args);
        logger.success("《《-启动成功-》》");
    }
}
