/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.domain.Personen;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonenDaoTest extends Setup{
    PersonenDaoPojo persoonTest123 = new PersonenDaoPojo();
    
    @Test
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
}
