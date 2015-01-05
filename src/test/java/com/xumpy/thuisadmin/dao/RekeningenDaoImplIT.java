/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nico
 */
public class RekeningenDaoImplIT {
    
    public RekeningenDaoImplIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of save method, of class RekeningenDaoImpl.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        Rekeningen rekeningen = null;
        RekeningenDaoImpl instance = new RekeningenDaoImpl();
        instance.save(rekeningen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class RekeningenDaoImpl.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Rekeningen rekeningen = null;
        RekeningenDaoImpl instance = new RekeningenDaoImpl();
        instance.update(rekeningen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class RekeningenDaoImpl.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Rekeningen rekeningen = null;
        RekeningenDaoImpl instance = new RekeningenDaoImpl();
        instance.delete(rekeningen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findAllRekeningen method, of class RekeningenDaoImpl.
     */
    @Test
    public void testFindAllRekeningen() {
        System.out.println("findAllRekeningen");
        RekeningenDaoImpl instance = new RekeningenDaoImpl();
        List<Rekeningen> expResult = null;
        List<Rekeningen> result = instance.findAllRekeningen();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
