/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Groepen;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class GroepenDaoTest extends H2InMemory{
    @Test
    public void testHoofdGroep(){
        Groepen hoofdGroep = groepenDao.findGroep(1);
        
        assertEquals(hoofdGroep, groepenDao.getHoofdGroep(groepenDao.findGroep(1)));
        assertEquals(hoofdGroep, groepenDao.getHoofdGroep(groepenDao.findGroep(2)));
        assertEquals(hoofdGroep, groepenDao.getHoofdGroep(groepenDao.findGroep(3)));
    }
}
