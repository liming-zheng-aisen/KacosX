package www.macos.demo;

import com.macos.framework.annotation.MacosXApplication;
import com.macos.framework.starter.run.MacosXApplicationRun;


/**
 * @Desc Application
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
@MacosXApplication
public class Application {
    public static void main(String[] args){
        MacosXApplicationRun.run(Application.class,args);
    }


}
