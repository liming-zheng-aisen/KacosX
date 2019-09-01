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

//    @DyBean
//    public MapperScannerConfigurer inintMapper(){
//        MapperScannerConfigurer mc=new MapperScannerConfigurer();
//        mc.setBasePackage("com.duanya.spring.web.mapper");
//        return mc;
//
//    }
//
//    @DyAutowired("dataSource")
//    private DataSource dataSource;
//
//    @DyBean
//    public MybatisSqlSessionFactoryBean initMSFB(){
//        MybatisSqlSessionFactoryBean msfb=new MybatisSqlSessionFactoryBean();
//        msfb.setDataSource(dataSource);
//        return msfb;
//    }

}
