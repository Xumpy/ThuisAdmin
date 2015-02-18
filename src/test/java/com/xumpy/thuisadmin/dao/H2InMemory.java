/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.*;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class H2InMemory {
    @Mock SessionFactory sessionFactory;
    
    private ServiceRegistry serviceRegistry;
    protected SessionFactory sessionFactoryH2;
    
    @InjectMocks PersonenDaoImpl personenDao;
    @InjectMocks GroepenDaoImpl groepenDao;
    @InjectMocks RekeningenDaoImpl rekeningenDao;
    @InjectMocks DocumentenDaoImpl documentenDao;
    @InjectMocks BedragenDaoImpl bedragenDao;
    
    @Before
    public void setUp(){
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
        
        sessionFactoryH2.getCurrentSession().beginTransaction();
        when(sessionFactory.getCurrentSession()).thenReturn(sessionFactoryH2.getCurrentSession());
    }
    
    @After
    public void tearDown(){
        sessionFactoryH2.close();
    }
}
