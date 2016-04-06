/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security.root;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author nicom
 */

@EnableTransactionManagement
@EnableJpaRepositories("com.xumpy.*")
public class InitDatabase { 
    private static Logger log = Logger.getLogger(InitDatabase.class);
    
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
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
    
    Properties hibernatePropertiesJPA() {
        return new Properties() {
            private static final long serialVersionUID = 1L;

            {
                setProperty("hibernate.dialect", hibernateDialect);
                setProperty("hibernate.globally_quoted_identifiers", "true");
                setProperty("hibernate.hbm2ddl.auto", "none");
                setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate4.SpringSessionContext");
                setProperty("hibernate.ejb.entitymanager_factory_name", "HIBERNATE_JPA");
             }
        };
    }
    
    @Bean
    @Primary
    @Qualifier(value="jpaTransactionManager")
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getNativeEntityManagerFactory());
        
        return txManager;
    }
    
    @Bean
    @Qualifier(value="transactionManager")
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        
        return txManager;
    }
    
    @Bean
     public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
     }
     
     @Bean
     public HibernateJpaVendorAdapter jpaVendorAdapter(){
         HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
         hibernateJpaVendorAdapter.setShowSql(true);
         hibernateJpaVendorAdapter.setGenerateDdl(true);
         hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
         
         return hibernateJpaVendorAdapter;
     }
     
     @Bean
     public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
         LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
         
         localContainerEntityManagerFactoryBean.setDataSource(dataSource());
         localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
         localContainerEntityManagerFactoryBean.setJpaProperties(hibernatePropertiesJPA());
         localContainerEntityManagerFactoryBean.setPackagesToScan("com.xumpy.thuisadmin.dao.*", 
                 "com.xumpy.timesheets.dao.*", 
                 "com.xumpy.collections.dao.*");
         
         return localContainerEntityManagerFactoryBean;
     }
}
