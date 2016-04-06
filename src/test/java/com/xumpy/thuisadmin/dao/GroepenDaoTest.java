/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.security.root.InitDatabase;
import com.xumpy.security.root.InitOldDatabase;
import com.xumpy.security.root.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author nicom
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitDatabase.class, InitOldDatabase.class, UserService.class})
@ActiveProfiles("junit")
public class GroepenDaoTest{
    @Test
    public void testHoofdGroep(){
        /*
        Groepen hoofdGroep = groepenDao.findGroep(1);
        
        assertEquals(hoofdGroep, groepenDao.getHoofdGroep(groepenDao.findGroep(1)));
        assertEquals(hoofdGroep, groepenDao.getHoofdGroep(groepenDao.findGroep(2)));
        assertEquals(hoofdGroep, groepenDao.getHoofdGroep(groepenDao.findGroep(3)));
                */
    }
}
