package com.mx.framework.nacos.balance;

import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.mx.framework.core.bean.manage.BeanManager;

import java.util.List;

/**
 * @Desc 默认nacos
 * @Author Zheng.LiMing
 * @Date 2019/9/17
 */
public class DefaultNacosBalanceServiceImpl implements NacosBalanceSerivce {

    @Override
    public Instance getHealthyInstance(String serviceName, String groupName, List<String> clusterName) throws Exception{
        BeanManager beanManager = new BeanManager();
        NamingService namingService = (NamingService)beanManager.getBean("namingService",NamingService.class);
        return namingService.selectOneHealthyInstance(serviceName,groupName,clusterName);
    }

}
