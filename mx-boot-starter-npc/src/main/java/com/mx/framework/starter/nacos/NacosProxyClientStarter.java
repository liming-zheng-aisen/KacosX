package com.mx.framework.starter.nacos;

import com.mx.common.util.StringUtils;
import com.mx.framework.annotation.boot.MacosXApplicationStarter;
import com.mx.framework.annotation.boot.NacosServiceClient;
import com.mx.framework.context.base.ApplicationContextApi;
import com.mx.framework.core.bean.definition.BeanDefinition;
import com.mx.framework.core.bean.manage.BeanManager;
import com.mx.framework.core.context.ApplicationContextImpl;
import com.mx.framework.core.env.ApplicationENV;
import com.mx.framework.nacos.rest.RestClient;
import com.mx.framework.starter.DefaultStarter;
import com.mx.framework.starter.nacos.proxy.NacosProxy;


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
