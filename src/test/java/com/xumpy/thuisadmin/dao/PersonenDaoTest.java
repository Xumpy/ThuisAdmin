/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.security.root.InitDatabase;
import com.xumpy.security.root.InitOldDatabase;
import com.xumpy.security.root.UserService;
import com.xumpy.thuisadmin.dao.implementations.PersonenDaoImpl;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.domain.Personen;
import com.xumpy.thuisadmin.services.PersonenSrv;
import com.xumpy.thuisadmin.services.implementations.PersonenSrvImpl;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicom
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitDatabase.class, InitOldDatabase.class, UserService.class, PersonenSrvImpl.class})
@ActiveProfiles("junit")
public class PersonenDaoTest{
    PersonenDaoPojo persoonTest123 = new PersonenDaoPojo();
    @Autowired PersonenSrv personenSrv;
    @Autowired PersonenDaoImpl personenDao;
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testSave(){
        persoonTest123.setPk_id(3);
        persoonTest123.setNaam("test123");
        persoonTest123.setVoornaam("test123");
        persoonTest123.setUsername("test123");
        persoonTest123.setMd5_password("test123");
        
        personenDao.save(persoonTest123);
        
        Personen persoonGetTest123 = personenDao.findPersoonByUsername("test123");
        
        assertEquals(persoonGetTest123.getNaam(), "test123");
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testSaveUser(){
        PersonenDaoPojo persoon1 = personenDao.findOne(1);
        persoon1.setMd5_password("");
        personenSrv.save(persoon1);
        Personen persoon2 = personenDao.findOne(1);
        
        assertEquals(persoon1.getMd5_password(), persoon2.getMd5_password());
    }
}
