package com.macos.framework.nacos.balance;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @Desc DyNacosBalanceSerivce
 * @Author Zheng.LiMing
 * @Date 2019/9/16
 */
public interface NacosBalanceSerivce {

    Instance getHealthyInstance(String serviceName,String groupName,List<String> clusterName) throws Exception;

}
