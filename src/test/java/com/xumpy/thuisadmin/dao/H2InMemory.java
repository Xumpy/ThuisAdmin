/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.model.db.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
@EnableTransactionManagement
public abstract class H2InMemory {
    @Mock SessionFactory sessionFactory;
    
    private static ServiceRegistry serviceRegistry;
    protected static SessionFactory sessionFactoryH2;
    private Transaction transaction;
    
    @Mock protected UserInfo userInfo;
    @Mock protected Personen persoon;
    
    @InjectMocks public PersonenDaoImpl personenDao;
    @InjectMocks public GroepenDaoImpl groepenDao;
    @InjectMocks public RekeningenDaoImpl rekeningenDao;
    @InjectMocks public DocumentenDaoImpl documentenDao;
    @InjectMocks public BedragenDaoImpl bedragenDao;
    
    @BeforeClass
    public static void setUp(){
        Configuration config = new Configuration();
        
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:scripts/createDB.sql'");
        config.setProperty("hibernate.current_session_context_class", "thread");        
        config.addAnnotatedClass(Personen.class);
        config.addAnnotatedClass(Bedragen.class);
        config.addAnnotatedClass(Documenten.class);
        config.addAnnotatedClass(Groepen.class);
        config.addAnnotatedClass(Rekeningen.class);
                
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactoryH2 = config.buildSessionFactory(serviceRegistry);
    }
    
    @Before
    public void setUpTest(){
        transaction = sessionFactoryH2.getCurrentSession().beginTransaction();
        when(sessionFactory.getCurrentSession()).thenReturn(sessionFactoryH2.getCurrentSession());
        when(userInfo.getPersoon()).thenReturn(persoon);
        when(persoon.getPk_id()).thenReturn(1);
    }
    
    @After
    public void tearDownTest(){
        transaction.rollback();
    }
    
    @AfterClass
    public static void tearDown(){
        sessionFactoryH2.close();
    }
}
