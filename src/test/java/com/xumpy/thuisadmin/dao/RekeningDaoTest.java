/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.setup.H2InMemory;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Nico
 */

@RunWith(MockitoJUnitRunner.class)
public class RekeningDaoTest extends H2InMemory{
    
    @Test
    public void testTotalRekening(){
        assertEquals(rekeningenDao.totalAllRekeningen(), new BigDecimal(2250));
    }
}
