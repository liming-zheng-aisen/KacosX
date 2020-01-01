package com.macos.framework.starter.nacos;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.MacosXApplicationStarter;
import com.macos.framework.annotation.NacosServiceClient;
import com.macos.framework.context.impl.ApplicationContextImpl;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.manager.ContextManager;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.nacos.rest.RestClient;
import com.macos.framework.starter.DefaultStarter;
import com.macos.framework.starter.nacos.proxy.NacosProxy;


import java.lang.reflect.Proxy;
import java.util.Properties;
import java.util.Set;

/**
 * @Desc nacos代理客户端
 * @Author Zheng.LiMing
 * @Date 2019/9/18
 */
@MacosXApplicationStarter(scannerPath = {},order = Integer.MAX_VALUE-100)
public class NacosProxyClientStarter implements DefaultStarter {

    @Override
    public void doStart(Properties evn, Class cl) throws Exception {

        Set<Class> classSet = BeanManager.getClassContainer();
        ContextManager manager= ContextManager.BuilderContext.getContextManager();
        RestClient restClient =(RestClient) manager.getBean("restClient",RestClient.class);
        if (null==restClient){
            restClient=new RestClient();
        }
        ApplicationContextApi context= ApplicationContextImpl.Builder.getDySpringApplicationContext();
        for (Class bean:classSet){
            if (bean.isAnnotationPresent(NacosServiceClient.class)){
              Object obj =  Proxy.newProxyInstance(bean.getClassLoader(),new Class[]{bean},new NacosProxy(restClient));
              String beanName= StringUtils.toLowerCaseFirstName(bean.getSimpleName());
              context.registerBean(beanName,obj);
              System.out.println(beanName);
            }
        }
    }
}
