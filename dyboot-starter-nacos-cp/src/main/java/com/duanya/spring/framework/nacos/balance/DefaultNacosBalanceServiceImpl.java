package com.duanya.spring.framework.nacos.balance;

import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.duanya.spring.framework.context.manager.DyContextManager;

import java.util.List;

/**
 * @Desc 默认nacos
 * @Author Zheng.LiMing
 * @Date 2019/9/17
 */
public class DefaultNacosBalanceServiceImpl implements DyNacosBalanceSerivce {

    @Override
    public Instance getHealthyInstance(String serviceName, String groupName, List<String> clusterName) throws Exception{
        DyContextManager context=DyContextManager.BuilderContext.getContextManager();
        NamingService namingService=(NamingService)context.getBean("namingService",NamingService.class);
        return namingService.selectOneHealthyInstance(serviceName,groupName,clusterName);
    }

}
