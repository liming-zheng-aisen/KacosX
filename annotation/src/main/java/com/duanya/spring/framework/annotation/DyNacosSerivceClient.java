package com.duanya.spring.framework.annotation;


/**
 * @author zheng.liming
 * @date 2019/9/15
 * @description nacos服务客户端http代理模式
 */
public @interface DyNacosSerivceClient {

    /**
     * 服务名称
     * @return
     */
    String servieName();

    /**
     * 分组
     * @return
     */
    String groupName();

    /**
     * 集群名字
     * @return
     */
    String[] clusterNames() default {};
}
