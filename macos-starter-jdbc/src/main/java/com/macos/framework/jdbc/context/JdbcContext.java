package com.macos.framework.jdbc.context;

import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.exception.ContextException;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.env.ApplicationENV;
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

    BeanManager beanManager = new BeanManager();


    @Override
    public Object getBean(String beanName,Class beanClass) throws Exception {
        Object bean=null;
        if (sqlSessionFactory!=null&&beanClass.isAnnotationPresent(Mapper.class)) {
            bean=jdbcContext.get(beanClass);
            ApplicationENV env= (ApplicationENV)beanManager.getBean(null,ApplicationENV.class);
            if (null==bean) {
                SqlSession sqlSession = sqlSessionFactory.openSession(Boolean.parseBoolean(env.getElementValue("dy.datasource.autoTransaction", "true").toString()));
                bean=sqlSession.getMapper(beanClass);
                jdbcContext.put(beanClass,bean);
            }
        }
       return bean;
    }


    @Override
    public boolean registerBean(String beanName, Object object) throws ContextException {
        if (sqlSessionFactory!=null&&object.getClass().isAnnotationPresent(Mapper.class)) {
            sqlSessionFactory.getConfiguration().addMapper(object.getClass());
        }
        return true;
    }

    @Override
    public Object registerBean(String beanName, Class object) throws ContextException {
        if (sqlSessionFactory!=null&&object.getClass().isAnnotationPresent(Mapper.class)) {
            sqlSessionFactory.getConfiguration().addMapper(object.getClass());
        }
        return null;
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
            return context;
        }
    }

}
