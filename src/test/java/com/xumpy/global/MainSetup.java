/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.global;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.domain.Personen;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author nicom
 * 
 * This mock is made for the mocking of default beans that are assigned and used through the application.
 * Only extend from this class if your test needs to inject those beans
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class MainSetup {
    @Mock public Personen loginPersoon;
    @Spy public UserInfo userInfo;
    @Mock public SessionFactory sessionFactory;
    
    public static Configuration config;
    public static SessionFactory sessionFactoryH2;
    public Transaction transaction;
    public static ServiceRegistry serviceRegistry;
    
    public static void startSession(){
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactoryH2 = config.buildSessionFactory(serviceRegistry);
    }
    
    @Before
    public void setupUserInfo(){
        when(loginPersoon.getNaam()).thenReturn("Test User");
        when(loginPersoon.getPk_id()).thenReturn(1);
        
        when(userInfo.getPersoon()).thenReturn(loginPersoon);
    }
    
    @BeforeClass
    public static void setupMainConfig(){
        config = new Configuration();
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:scripts/createDB.sql'");
        config.setProperty("hibernate.current_session_context_class", "thread");    
    }
    
    @Before
    public void setUpTest(){
        transaction = sessionFactoryH2.getCurrentSession().beginTransaction();
        when(sessionFactory.getCurrentSession()).thenReturn(sessionFactoryH2.getCurrentSession());
    }
    
    @After
    public void tearDownTest(){
        transaction.rollback();
    }
    
    @AfterClass
    public static void tearDown(){
        sessionFactoryH2.close();
    }
    
    public void resetTransaction(){
        sessionFactoryH2.getCurrentSession().getTransaction().commit();
        sessionFactoryH2 = config.buildSessionFactory(serviceRegistry);
        transaction = sessionFactoryH2.getCurrentSession().beginTransaction();
        when(sessionFactory.getCurrentSession()).thenReturn(sessionFactoryH2.getCurrentSession());
    }
}
