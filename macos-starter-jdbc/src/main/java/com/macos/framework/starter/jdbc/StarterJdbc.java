package com.macos.framework.starter.jdbc;

import com.macos.common.scanner.api.ScannerApi;
import com.macos.common.scanner.impl.ScannerImpl;
import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.MacosXApplication;
import com.macos.framework.annotation.MacosXApplicationStarter;
import com.macos.framework.annotation.MacosXScanner;

import com.macos.framework.core.bean.BeanManager;
import com.macos.framework.druid.DruidFilter;
import com.macos.framework.druid.DruidServlet;
import com.macos.framework.jdbc.config.DruidConfig;
import com.macos.framework.jdbc.context.JdbcContext;
import com.github.pagehelper.PageInterceptor;
import com.macos.framework.starter.DefaultStarter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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

@MacosXApplicationStarter(scannerPath = {}, order =Integer.MAX_VALUE-100)
public class StarterJdbc implements DefaultStarter {

    private static final String DEF_STATUS="development";

    @Override
    public void doStart(Properties evn, Class cl) throws Exception {
        ScannerApi scanner=new ScannerImpl();
        Set<Class> result=new HashSet<>();
        if (cl.isAnnotationPresent(MacosXScanner.class)){
            MacosXScanner dyMacosXScanner =(MacosXScanner)cl.getAnnotation(MacosXScanner.class);
            String[] scanPaths= dyMacosXScanner.packageNames();

            for (String p:scanPaths){
                if (StringUtils.isNotEmptyPlus(p)) {
                     Set<Class> scannerClass = scanner.doScanner(p,Mapper.class);
                     result.addAll(scannerClass);
                }
            }
        }else if (cl.isAnnotationPresent(MacosXApplication.class)){
           result.addAll(scanner.doScanner(cl.getPackage().getName(),Mapper.class));
        }
        Set<Class> classSet=new HashSet<>();
        classSet.add(DruidFilter.class);
        classSet.add(DruidServlet.class);

        String ignorePath=evn.getProperty("server.ignorePath");
        if (StringUtils.isEmptyPlus(ignorePath)) {
            evn.setProperty("server.ignorePath", "/druid/*,/download/*");
        }
        BeanManager.registerClassBySet(classSet);
        registerDyJdbcContext(result,evn);
    }

    private void registerDyJdbcContext(Set<Class> result,Properties evn){

        DruidConfig druidConfig=new DruidConfig();

        druidConfig.setFilters("stat");

        DataSource dataSource= druidConfig.druidDataSource(evn);
        Environment environment=new Environment(DEF_STATUS,new JdbcTransactionFactory(),dataSource);
        Configuration configuration=new Configuration(environment);
        //骆驼命名开启
        configuration.setMapUnderscoreToCamelCase(Boolean.parseBoolean(evn.getProperty("datasource.mapUnderscoreToCamelCase","true")));

        for (Class cl:result){
            configuration.addMapper(cl);
        }

        SqlSessionFactory sqlSessionFactory= new SqlSessionFactoryBuilder().build(configuration);;

        PageInterceptor pageInterceptor=new PageInterceptor();

        Properties properties=new Properties();
        properties.setProperty("dialect",evn.getProperty("datasource.driver-class-name",druidConfig.getDriverClassName()));
        //pageInterceptor.setProperties(properties);
        configuration.addInterceptor(pageInterceptor);
        JdbcContext jdbcContext=  JdbcContext.Builder.getDySpringApplicationContext();
        jdbcContext.setSqlSessionFactory(sqlSessionFactory);



    }

}
