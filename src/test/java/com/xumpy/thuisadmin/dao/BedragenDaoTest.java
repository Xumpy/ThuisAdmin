/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.db.Personen;
import java.math.BigDecimal;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class BedragenDaoTest extends H2InMemory{
    
    private Personen persoon;
    private Groepen groep;
    
    @Before
    @Transactional(readOnly=false)
    public void setUpBedragen(){
        persoon = personenDao.findPersoon(1);
        groep = groepenDao.findGroep(1);
    }
    
    @Test
    @Transactional(readOnly=false)
    public void testSave(){
        Bedragen bedrag = new Bedragen();
        
        bedrag.setPk_id(1);
        bedrag.setBedrag(new BigDecimal(200));
        bedrag.setPersoon(persoon);
        bedrag.setGroep(groep);
        bedrag.setDatum(new Date());
        bedrag.setOmschrijving("test bedrag");
        
        bedragenDao.save(bedrag);
        sessionFactoryH2.getCurrentSession().flush();
        
        BigDecimal bedragTest = bedragenDao.getBedrag(1);
        
        assertEquals(new BigDecimal("200"), bedragTest);
    }
}
