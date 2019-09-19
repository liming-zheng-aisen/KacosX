package com.duanya.spring.framework.starter.nacos.proxy;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyBootApplicationStarter;
import com.duanya.spring.framework.annotation.DyNacosServiceClient;
import com.duanya.spring.framework.context.manager.DyContextManager;
import com.duanya.spring.framework.context.spring.DySpringApplicationContext;
import com.duanya.spring.framework.core.bean.factory.bean.manager.DyBeanManager;
import com.duanya.spring.framework.nacos.rest.RestClient;
import com.duanya.spring.framework.starter.DyDefaultStarter;
import com.duanya.spring.framework.starter.nacos.proxy.proxy.DyNacosProxy;

import java.lang.reflect.Proxy;
import java.util.Properties;
import java.util.Set;

/**
 * @Desc nacos代理客户端
 * @Author Zheng.LiMing
 * @Date 2019/9/18
 */
@DyBootApplicationStarter(scannerPath = {},order = Integer.MAX_VALUE-100)
public class DyNacosProxyClientStarter implements DyDefaultStarter {

    @Override
    public void doStart(Properties evn, Class cl) throws Exception {

        Set<Class> classSet=DyBeanManager.getClassContainer();
        DyContextManager manager=DyContextManager.BuilderContext.getContextManager();
        RestClient restClient =(RestClient) manager.getBean("restClient",RestClient.class);
        if (null==restClient){
            restClient=new RestClient();
        }
        DySpringApplicationContext context=DySpringApplicationContext.Builder.getDySpringApplicationContext();
        for (Class bean:classSet){
            if (bean.isAnnotationPresent(DyNacosServiceClient.class)){
              Object obj =  Proxy.newProxyInstance(bean.getClassLoader(),new Class[]{bean},new DyNacosProxy(restClient));
              String beanName=StringUtils.toLowerCaseFirstName(bean.getSimpleName());
              context.registerBean(beanName,obj);
              System.out.println(beanName);
            }
        }
    }
}
