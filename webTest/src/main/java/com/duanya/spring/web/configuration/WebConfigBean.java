package com.duanya.spring.web.configuration;

import com.duanya.spring.framework.core.annotation.DyBean;
import com.duanya.spring.framework.core.annotation.DyConfiguration;
import com.duanya.spring.web.domain.Students;

/**
 * @author zheng.liming
 * @date 2019/8/24
 * @description
 */
@DyConfiguration
public class WebConfigBean {

    @DyBean("jiahua")
    public Students initStuBean(){
        return new Students("1344","java",100);
    }
}
