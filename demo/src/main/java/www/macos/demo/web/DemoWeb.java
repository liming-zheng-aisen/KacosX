package www.macos.demo.web;

import com.mx.framework.annotation.core.Autowired;
import com.mx.framework.annotation.http.*;
import www.macos.demo.config.DemoConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc DemoWeb
 * @Author Zheng.LiMing
 * @Date 2020/2/21
 */
@RestAPI
@RequestMapping(value = "/DemoWeb")
public class DemoWeb {

    @Autowired
    private DemoConfig demoConfig;

    @Get
    public String get(){
        return demoConfig.getVersion();
    }

    @Post
    public String post(){
        return demoConfig.getVersion();
    }

    @Put
    public String put(){
        return demoConfig.getVersion();
    }

    @Delete
    public String delete(){
        return demoConfig.getVersion();
    }

    @Get("/{id}")
    public String getId(@PathVariable String id){
        return id;
    }

    @Get("/http/do")
    public String doHttp(HttpServletRequest request){
        return request.getRequestURI();
    }
}
