package com.macos.framework.jdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Aisen
 */
public class DruidConfig {

    private Logger logger = LoggerFactory.getLogger(DruidConfig.class);

    private String dbUrl="jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=true";

    private String username="root";

    private String password="123456";

    private String driverClassName="com.mysql.cj.jdbc.Driver";

    private int initialSize=100;

    private int minIdle=50;

    private int maxActive=200;

    private int maxWait=2000;

    private int timeBetweenEvictionRunsMillis=60000;

    private int minEvictableIdleTimeMillis=300000;

    private String validationQuery="SELECT 1 FROM DUAL";

    private boolean testWhileIdle=true;

    private boolean testOnBorrow=false;

    private boolean testOnReturn=false;

    private String filters="stat,wall,log4j";


    public DataSource druidDataSource(Properties evn) {

        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(evn.getProperty("datasource.url",dbUrl));
        datasource.setUsername(evn.getProperty("datasource.username",username));
        datasource.setPassword(evn.getProperty("datasource.password",password));
        datasource.setDriverClassName(evn.getProperty("datasource.driver-class-name",driverClassName));
        datasource.setInitialSize(Integer.parseInt(evn.getProperty("datasource.initialSize",initialSize+"")));
        datasource.setMinIdle(Integer.parseInt(evn.getProperty("datasource.minIdle",minIdle+"")));
        datasource.setMaxActive(Integer.parseInt(evn.getProperty("datasource.maxActive",maxActive+"")));
        datasource.setMaxWait(Integer.parseInt(evn.getProperty("datasource.maxWait",maxWait+"")));
        datasource.setTimeBetweenEvictionRunsMillis(Integer.parseInt(evn.getProperty("datasource.timeBetweenEvictionRunsMillis",timeBetweenEvictionRunsMillis+"")));
        datasource.setMinEvictableIdleTimeMillis(Integer.parseInt(evn.getProperty("datasource.minEvictableIdleTimeMillis",minEvictableIdleTimeMillis+"")));
        datasource.setValidationQuery(evn.getProperty("datasource.validationQuery",validationQuery));
        datasource.setTestWhileIdle(Boolean.parseBoolean(evn.getProperty("datasource.testWhileIdle",testWhileIdle+"")));
        datasource.setTestOnBorrow(Boolean.parseBoolean(evn.getProperty("datasource.testOnBorrow",testOnBorrow+"")));
        datasource.setTestOnReturn(Boolean.parseBoolean(evn.getProperty("datasource.testOnReturn",testOnReturn+"")));
        datasource.setRemoveAbandoned(true);
        datasource.setRemoveAbandonedTimeout(1800);
        datasource.setLogAbandoned(true);
        datasource.setDefaultAutoCommit(true);
        try {
            datasource.setFilters(evn.getProperty("datasource.filters",filters));
        } catch (SQLException e) {
            logger.error("Druid configuration initialization filter error", e);
        }
        return datasource;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }
}
