/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security.root;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 *
 * @author nicom
 */

@ComponentScan({"com.xumpy.thuisadmin.dao.*"})
public class InitDatabase { 
    @Configuration
    @Profile("dev")
    @PropertySource("classpath:dev-database.properties")
    static class Dev{ }
    
    @Configuration
    @Profile("tst")
    @PropertySource("classpath:tst-database.properties")
    static class tst{ }
    
    @Configuration
    @Profile("prd")
    @PropertySource("classpath:prd-database.properties")
    static class prd{ }
    
    @Configuration
    @Profile("tst_local")
    @PropertySource("classpath:tst_local-database.properties")
    static class tst_local{ }
    
    @Value("${jdbc.driverclass}") private String driverClassName;
    @Value("${jdbc.url}") private String url;
    @Value("${jdbc.username}") private String username;
    @Value("${jdbc.password}") private String password;
    @Value("${hibernate.dialect}") private String hibernateDialect;
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.xumpy.thuisadmin" });
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
    
    Properties hibernateProperties() {
        return new Properties() {
            private static final long serialVersionUID = 1L;

            {
                setProperty("hibernate.dialect", hibernateDialect);
                setProperty("hibernate.globally_quoted_identifiers", "true");
             }
        };
    }
    
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        
        return txManager;
    }
    
    @Bean
     public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
     }
}
