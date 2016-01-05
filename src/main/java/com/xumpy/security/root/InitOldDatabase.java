/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security.root;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 *
 * @author nico
 */
@ComponentScan({"com.xumpy.dao.*"})
@Configuration
public class InitOldDatabase {
    @Autowired LocalContainerEntityManagerFactoryBean entityManagerFactory;
     
    private static Logger log = Logger.getLogger(InitOldDatabase.class);
    
    @Bean
    public SessionFactory sessionFactory(){
        Session session = (Session) entityManagerFactory.getObject().createEntityManager().getDelegate();

        log.info(entityManagerFactory.nativeEntityManagerFactory);
        
        return session.getSessionFactory();
    }
}
