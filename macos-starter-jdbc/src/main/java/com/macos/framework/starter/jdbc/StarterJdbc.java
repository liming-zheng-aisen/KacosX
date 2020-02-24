package com.macos.framework.starter.jdbc;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.MacosXApplicationStarter;

import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.env.ApplicationENV;
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
    public void doStart(ApplicationENV env, Class main , String[] arg) throws Exception {
        Set<Class> classSet=new HashSet<>();
        classSet.add(DruidFilter.class);
        classSet.add(DruidServlet.class);
        String ignorePath=env.getElementValue("server.ignorePath").toString();
        if (StringUtils.isEmptyPlus(ignorePath)) {
            env.addElement("server.ignorePath", "/druid/*,/download/*");
        }
        BeanManager.registerClassBySet(classSet);
        Set<BeanDefinition> beanDefinitionSet = BeanManager.getBeanDefinitionsByAnnotation(Mapper.class);
        registerDyJdbcContext(beanDefinitionSet,env);
    }

    private void registerDyJdbcContext(Set<BeanDefinition> result,ApplicationENV evn){

        DruidConfig druidConfig=new DruidConfig();

        druidConfig.setFilters("stat");

        DataSource dataSource= druidConfig.druidDataSource(evn);
        Environment environment=new Environment(DEF_STATUS,new JdbcTransactionFactory(),dataSource);
        Configuration configuration=new Configuration(environment);
        //骆驼命名开启
        configuration.setMapUnderscoreToCamelCase(Boolean.parseBoolean(evn.getElementValue("datasource.mapUnderscoreToCamelCase","true").toString()));

        for (BeanDefinition cl:result){
            configuration.addMapper(cl.getTarget());
        }

        SqlSessionFactory sqlSessionFactory= new SqlSessionFactoryBuilder().build(configuration);;

        PageInterceptor pageInterceptor=new PageInterceptor();

        Properties properties=new Properties();
        properties.setProperty("dialect",evn.getElementValue("datasource.driver-class-name",druidConfig.getDriverClassName()).toString());
        //pageInterceptor.setProperties(properties);
        configuration.addInterceptor(pageInterceptor);
        JdbcContext jdbcContext=  JdbcContext.Builder.getDySpringApplicationContext();
        jdbcContext.setSqlSessionFactory(sqlSessionFactory);
    }

}
