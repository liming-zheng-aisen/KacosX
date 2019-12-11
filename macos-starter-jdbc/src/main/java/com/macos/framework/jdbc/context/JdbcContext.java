package com.macos.framework.jdbc.context;

import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.exception.ContextException;
import com.macos.framework.context.manager.ContextManager;
import com.macos.framework.core.load.conf.ConfigurationLoader;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @Desc JDBC上下文
 * @Author Zheng.LiMing
 * @Date 2019/9/5
 */
public class JdbcContext implements ApplicationContextApi {

    private SqlSessionFactory sqlSessionFactory;

    private Map<Class,Object> jdbcContext=new HashMap<>();

    @Override
    public Object getBean(String beanName,Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Object bean=null;
        if (sqlSessionFactory!=null&&beanClass.isAnnotationPresent(Mapper.class)) {
            bean=jdbcContext.get(beanClass);
            if (null==bean) {
                SqlSession sqlSession = sqlSessionFactory.openSession(Boolean.parseBoolean(ConfigurationLoader.getEvn().getProperty("dy.datasource.autoTransaction", "true")));
                bean=sqlSession.getMapper(beanClass);
                jdbcContext.put(beanClass,bean);
            }
        }
       return bean;
    }


    @Override
    public void registerBean(String beanName, Object object) throws ContextException {
        if (sqlSessionFactory!=null&&object.getClass().isAnnotationPresent(Mapper.class)) {
            sqlSessionFactory.getConfiguration().addMapper(object.getClass());
        }
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public static class Builder{

        /**单例模式*/
        private final static JdbcContext context=new JdbcContext();

        public static JdbcContext getDySpringApplicationContext(){
            ContextManager contextManager= ContextManager.BuilderContext.getContextManager();
            if (!contextManager.hasContext(context)){
               contextManager.registerApplicationContext(context);
            }
            return context;
        }
    }

}
