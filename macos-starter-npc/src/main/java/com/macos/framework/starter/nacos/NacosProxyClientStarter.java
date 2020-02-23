package com.macos.framework.starter.nacos;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.MacosXApplicationStarter;
import com.macos.framework.annotation.NacosServiceClient;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.context.ApplicationContextImpl;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.nacos.rest.RestClient;
import com.macos.framework.starter.DefaultStarter;
import com.macos.framework.starter.nacos.proxy.NacosProxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Set;

/**
 * @Desc nacos代理客户端
 * @Author Zheng.LiMing
 * @Date 2019/9/18
 */
@MacosXApplicationStarter(scannerPath = {},order = Integer.MAX_VALUE-100)
public class NacosProxyClientStarter implements DefaultStarter {

    @Override
    public void doStart(ApplicationENV env, Class main , String[] args) throws Exception {

        Set<BeanDefinition> classSet = BeanManager.getClassContainer();
        BeanManager beanManager = new BeanManager();
        RestClient restClient =(RestClient) beanManager.getBean("restClient",RestClient.class);
        if (null==restClient){
            restClient=new RestClient();
        }
        ApplicationContextApi context= ApplicationContextImpl.Builder.getApplicationContext();
        for (BeanDefinition beanDefinition:classSet){
            Class bean = beanDefinition.getTarget();
            if (bean.isAnnotationPresent(NacosServiceClient.class)){
              Object obj =  Proxy.newProxyInstance(bean.getClassLoader(),new Class[]{bean}, (InvocationHandler) new NacosProxy(restClient));
              String beanName= StringUtils.toLowerCaseFirstName(bean.getSimpleName());
              context.registerBean(beanName,obj);
            }
        }
    }
}
