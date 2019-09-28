package com.duanya.spring.framework.jdbc.context;

import com.duanya.spring.framework.context.base.DyApplicationContext;
import com.duanya.spring.framework.context.exception.DyContextException;
import com.duanya.spring.framework.context.manager.DyContextManager;
import com.duanya.spring.framework.core.load.DyConfigurationLoader;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @Desc DyJdbcContext
 * @Author Zheng.LiMing
 * @Date 2019/9/5
 */
public class DyJdbcContext implements DyApplicationContext {

    private SqlSessionFactory sqlSessionFactory;

    private Map<Class,Object> jdbcContext=new HashMap<>();

    @Override
    public Object getBean(String beanName,Class beanClass) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Object bean=null;
        if (sqlSessionFactory!=null&&beanClass.isAnnotationPresent(Mapper.class)) {
            bean=jdbcContext.get(beanClass);
            if (null==bean) {
                SqlSession sqlSession = sqlSessionFactory.openSession(Boolean.parseBoolean(DyConfigurationLoader.getEvn().getProperty("dy.datasource.autoTransaction", "true")));
                bean=sqlSession.getMapper(beanClass);
                jdbcContext.put(beanClass,bean);
            }
        }
       return bean;
    }


    @Override
    public void registerBean(String beanName, Object object) throws DyContextException {
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
        private final static DyJdbcContext context=new DyJdbcContext();

        public static  DyJdbcContext getDySpringApplicationContext(){
            DyContextManager contextManager=DyContextManager.BuilderContext.getContextManager();
            if (!contextManager.hasContext(context)){
               contextManager.registerApplicationContext(context);
            }
            return context;
        }
    }

}
