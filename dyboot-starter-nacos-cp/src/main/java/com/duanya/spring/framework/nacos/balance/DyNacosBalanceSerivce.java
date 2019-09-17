package com.duanya.spring.framework.nacos.balance;

import com.alibaba.nacos.api.naming.pojo.Instance;

/**
 * @Desc DyNacosBalanceSerivce
 * @Author Zheng.LiMing
 * @Date 2019/9/16
 */
public interface DyNacosBalanceSerivce {

    Instance getHealthyInstance(String serviceName,String groupName,String clusterName);


}
