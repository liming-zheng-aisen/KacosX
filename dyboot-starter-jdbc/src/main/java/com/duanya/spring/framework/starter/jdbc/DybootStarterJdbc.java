package com.duanya.spring.framework.starter.jdbc;

import com.duanya.spring.common.scanner.api.IDyScanner;
import com.duanya.spring.common.scanner.impl.DyScannerImpl;
import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyBootApplication;
import com.duanya.spring.framework.annotation.DyBootApplicationStarter;
import com.duanya.spring.framework.annotation.DyScanner;
import com.duanya.spring.framework.core.bean.factory.bean.manager.DyBeanManager;
import com.duanya.spring.framework.druid.DruidFilter;
import com.duanya.spring.framework.druid.DruidServlet;
import com.duanya.spring.framework.jdbc.config.DruidConfig;
import com.duanya.spring.framework.jdbc.context.DyJdbcContext;
import com.duanya.spring.framework.starter.DyDefaultStarter;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @Desc DybootStarterJdbc
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */

@DyBootApplicationStarter(scannerPath = {}, order =Integer.MAX_VALUE-100)
public class DybootStarterJdbc  implements DyDefaultStarter {

    private static final String DEF_STATUS="development";

    @Override
    public void doStart(Properties evn, Class cl) throws Exception {
        IDyScanner scanner=new DyScannerImpl();
        Set<Class> result=new HashSet<>();
        if (cl.isAnnotationPresent(DyScanner.class)){
            DyScanner dyScanner=(DyScanner)cl.getAnnotation(DyScanner.class);
            String[] scanPaths=dyScanner.packageNames();

            for (String p:scanPaths){
                if (StringUtils.isNotEmptyPlus(p)) {
                     Set<Class> scannerClass = scanner.doScanner(p,Mapper.class);
                     result.addAll(scannerClass);
                }
            }
        }else if (cl.isAnnotationPresent(DyBootApplication.class)){
           result.addAll(scanner.doScanner(cl.getPackage().getName(),Mapper.class));
        }
        Set<Class> classSet=new HashSet<>();
        classSet.add(DruidFilter.class);
        classSet.add(DruidServlet.class);

        String ignorePath=evn.getProperty("dy.server.ignorePath");
        if (StringUtils.isEmptyPlus(ignorePath)) {
            evn.setProperty("dy.server.ignorePath", "/druid/*,/download/*");
        }
        DyBeanManager.registerClassBySet(classSet);
        registerDyJdbcContext(result,evn);
    }

    private void registerDyJdbcContext(Set<Class> result,Properties evn){

        DruidConfig druidConfig=new DruidConfig();

        druidConfig.setFilters("stat");

        DataSource dataSource= druidConfig.druidDataSource(evn);
        Environment environment=new Environment(DEF_STATUS,new JdbcTransactionFactory(),dataSource);
        Configuration configuration=new Configuration(environment);
        //骆驼命名开启
        configuration.setMapUnderscoreToCamelCase(Boolean.parseBoolean(evn.getProperty("dy.datasource.mapUnderscoreToCamelCase","true")));

        SqlSessionFactory sqlSessionFactory= new DefaultSqlSessionFactory(configuration);

        PageInterceptor pageInterceptor=new PageInterceptor();

        Properties properties=new Properties();
        properties.setProperty("dialect",evn.getProperty("dy.datasource.driver-class-name",druidConfig.getDriverClassName()));
        //pageInterceptor.setProperties(properties);
        configuration.addInterceptor(pageInterceptor);
        //自动提交事务
        for (Class cl:result){
            configuration.addMapper(cl);
        }

        DyJdbcContext jdbcContext=  DyJdbcContext.Builder.getDySpringApplicationContext();
        jdbcContext.setSqlSessionFactory(sqlSessionFactory);



    }

}
