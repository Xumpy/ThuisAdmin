/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.global.MainSetup;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.PersonenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.GroepenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.DocumentenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragenTotal;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class Setup extends MainSetup {
    @Spy public OverzichtGroepBedragenTotal overzichtGroepBedragenTotal;
    
    @InjectMocks public PersonenDaoImpl personenDao;
    @InjectMocks public GroepenDaoImpl groepenDao;
    @InjectMocks public RekeningenDaoImpl rekeningenDao;
    @InjectMocks public DocumentenDaoImpl documentenDao;
    @InjectMocks public BedragenDaoImpl bedragenDao;
    
    @BeforeClass
    public static void setUp(){
        config.addAnnotatedClass(PersonenDaoPojo.class);
        config.addAnnotatedClass(BedragenDaoPojo.class);
        config.addAnnotatedClass(DocumentenDaoPojo.class);
        config.addAnnotatedClass(GroepenDaoPojo.class);
        config.addAnnotatedClass(RekeningenDaoPojo.class);
                
        startSession();
    }
}
