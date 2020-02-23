package www.macos.demo.web;

import com.macos.framework.annotation.Autowired;
import com.macos.framework.annotation.Get;
import com.macos.framework.annotation.RequestMapping;
import com.macos.framework.annotation.RestAPI;
import www.macos.demo.config.DemoConfig;

/**
 * @Desc DemoWeb
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
@RestAPI
@RequestMapping(value = "/")
public class DemoWeb {

    @Autowired
    private DemoConfig demoConfig;

    @Get
    public String getVersion(){
        return demoConfig.getVersion();
    }
}
