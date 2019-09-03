package com.duanya.spring.framework.jdbc.datasource;

import com.duanya.spring.commont.util.StringUtils;
import com.duanya.spring.framework.context.exception.DyContextException;
import com.duanya.spring.framework.context.spring.DySpringApplicationContent;
import com.duanya.spring.framework.core.bean.factory.DyBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author zheng.liming
 * @date 2019/8/26
 * @description 数据源加载
 */

public class DyDataSourceFactory {

    private static final Logger log = LoggerFactory.getLogger(DyDataSourceFactory.class);
    public static final String DATA_SOURCE_NAME="dataSource";
    private static DySpringApplicationContent dySpringApplicationContent=new DySpringApplicationContent();
    public static final String PREFIX="dy.datasource.";
    public static final String DATA_SOURCE_TYPE="dy.datasource.type";
    public static boolean exist(){
        Object bean=null;
        try {
           bean= dySpringApplicationContent.getBean(DATA_SOURCE_NAME);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null!=bean;
    }
    public static void createDataSource(Properties env){
        if (!exist()) {
            String type = env.getProperty(DATA_SOURCE_TYPE);
            if (StringUtils.isNotEmptyPlus(type)) {
                try {
                    Class clazz = Class.forName(type);
                    Object dataSource = clazz.newInstance();
                    List<Field> fields = getFields(clazz);
                    for (Field fd : fields) {
                        String key = PREFIX + fd.getName();
                        String value = env.getProperty(key);
                        if (StringUtils.isNotEmpty(value)) {
                            DyBeanFactory.setField(fd, dataSource, value);
                        }
                    }
                    dySpringApplicationContent.setContextBean(DATA_SOURCE_NAME, dataSource);
                    log.info("数据源加载成功！");
                } catch (ClassNotFoundException e) {
                    //....
                    log.error(e.getMessage());
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage());
                } catch (InstantiationException e) {
                    log.error(e.getMessage());
                } catch (DyContextException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    public static List<Field> getFields(Class c){
        List<Field> fields=new ArrayList<>();
        while (c!=null&&!c.getName().toLowerCase().equals("java.lang.object")){
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
            c=c.getSuperclass();
        }
        return fields;
    }

}
