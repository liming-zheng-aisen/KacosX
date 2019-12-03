package com.macos.framework.nacos.balance;

import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.macos.framework.context.manager.ContextManager;

import java.util.List;

/**
 * @Desc 默认nacos
 * @Author Zheng.LiMing
 * @Date 2019/9/17
 */
public class DefaultNacosBalanceServiceImpl implements NacosBalanceSerivce {

    @Override
    public Instance getHealthyInstance(String serviceName, String groupName, List<String> clusterName) throws Exception{
        ContextManager context = ContextManager.BuilderContext.getContextManager();
        NamingService namingService = (NamingService)context.getBean("namingService",NamingService.class);
        return namingService.selectOneHealthyInstance(serviceName,groupName,clusterName);
    }

}
