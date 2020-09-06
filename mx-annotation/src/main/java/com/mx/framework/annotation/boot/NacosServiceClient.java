package com.mx.framework.annotation.boot;


import java.lang.annotation.*;

/**
 * @author zheng.liming
 * @date 2019/9/15
 * @description nacos服务客户端http代理模式
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
public @interface NacosServiceClient {

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
