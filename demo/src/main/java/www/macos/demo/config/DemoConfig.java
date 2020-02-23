package www.macos.demo.config;

import com.macos.framework.annotation.Configuration;
import com.macos.framework.annotation.Value;

/**
 * @Desc demoConfig
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
@Configuration
public class DemoConfig {

    @Value("${version:1.0.0}")
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
