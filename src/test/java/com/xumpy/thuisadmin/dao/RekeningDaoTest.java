/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.Application;
import com.xumpy.security.model.UserInfo;
import com.xumpy.security.root.UserService;
import com.xumpy.thuisadmin.dao.implementations.PersonenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nico
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebAppConfiguration()
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("junit-database")
@Sql(scripts="/data.sql")
public class RekeningDaoTest{
    
    @Autowired RekeningenDaoImpl rekeningenDao;
    @Autowired PersonenDaoImpl personenDao;
    @Autowired UserInfo userInfo;
    
    @Test
    @Transactional
    public void testTotalRekening(){
        userInfo.setPersoon(personenDao.findOne(1));
        assertEquals(rekeningenDao.totalAllRekeningen(userInfo.getPersoon().getPk_id()), new BigDecimal(2250).setScale(2));
    }
}
