/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.security.model.UserInfo;
import com.xumpy.security.root.InitDatabase;
import com.xumpy.security.root.InitOldDatabase;
import com.xumpy.security.root.UserService;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import java.math.BigDecimal;
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
 * @author Nico
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitDatabase.class, InitOldDatabase.class, UserService.class})
@ActiveProfiles("junit")
public class RekeningDaoTest{
    
    @Autowired RekeningenDaoImpl rekeningenDao;
    @Autowired PersonenDao personenDao;
    @Autowired UserInfo userInfo;
    
    @Test
    @Transactional
    public void testTotalRekening(){
        userInfo.setPersoon(personenDao.findPersoon(1));
        assertEquals(rekeningenDao.totalAllRekeningen(), new BigDecimal(2250));
    }
}
