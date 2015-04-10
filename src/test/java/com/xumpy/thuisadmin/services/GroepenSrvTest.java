/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;
import com.xumpy.thuisadmin.model.Bedragen;
import com.xumpy.thuisadmin.model.Groepen;
import com.xumpy.thuisadmin.services.implementations.GroepenSrvImpl;
import com.xumpy.thuisadmin.services.model.GroepenSrvPojo;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */

@RunWith(MockitoJUnitRunner.class)
public class GroepenSrvTest {
    
    @Mock Groepen groep1;
    @Mock Groepen groep2;
    @Mock Groepen groep3;
    @Mock Groepen groep4;

    @InjectMocks GroepenSrvImpl groepenSrv;
    
    @Test
    public void testGetHoofdGroep(){
        when(groep4.getHoofdGroep()).thenReturn(groep3);
        when(groep3.getHoofdGroep()).thenReturn(groep2);
        when(groep2.getHoofdGroep()).thenReturn(groep1);
        
        assertEquals(groep1, GroepenSrvImpl.getHoofdGroep(groep4));
    }
    
    @Test
    public void testNoLinkHoofdGroep(){
        assertEquals(groep4, GroepenSrvImpl.getHoofdGroep(groep4));
    }
    
    @Test
    public void testNullHoofdGroep(){
        assertEquals(null, GroepenSrvImpl.getHoofdGroep(new GroepenSrvPojo()).getPk_id());
    }
    
    @Test 
    public void testTransformedHoofdGroep(){
        when(groep4.getHoofdGroep()).thenReturn(groep3);
        when(groep3.getHoofdGroep()).thenReturn(groep2);
        when(groep2.getHoofdGroep()).thenReturn(groep1);
        
        when(groep1.getHoofdGroep()).thenReturn(new GroepenSrvPojo()); // Tricky but possible
        
        assertEquals(groep1.getPk_id(), GroepenSrvImpl.getHoofdGroep(groep4).getPk_id());
    }
    
    @Test
    public void testEqualityGroepen(){
        GroepenSrvPojo groepEq1 = new GroepenSrvPojo();
        GroepenSrvPojo groepEq2 = new GroepenSrvPojo();
        
        groepEq1.setPk_id(1);
        groepEq2.setPk_id(1);
        
        if (groepEq1.equals(groepEq2)){
            System.out.println("---- Equals");
        }
        assertEquals(groepEq1, groepEq2);
    }
    
    @Test
    public void testFindAllHoofdGroepenWithBedragen(){
        when(groep1.getHoofdGroep()).thenReturn(null);
        when(groep2.getHoofdGroep()).thenReturn(null);
        when(groep3.getHoofdGroep()).thenReturn(groep1);
        when(groep4.getHoofdGroep()).thenReturn(groep3);
        
        
        List<Bedragen> allBedragen = new ArrayList<Bedragen>();
        Bedragen bedrag1 = Mockito.mock(Bedragen.class);
        Bedragen bedrag2 = Mockito.mock(Bedragen.class);
        Bedragen bedrag3 = Mockito.mock(Bedragen.class);
        Bedragen bedrag4 = Mockito.mock(Bedragen.class);
        Bedragen bedrag5 = Mockito.mock(Bedragen.class);
        
        when(bedrag1.getGroep()).thenReturn(groep1);
        when(bedrag2.getGroep()).thenReturn(groep2);
        when(bedrag3.getGroep()).thenReturn(groep3);
        when(bedrag4.getGroep()).thenReturn(groep4);
        when(bedrag5.getGroep()).thenReturn(groep2);
        
        allBedragen.add(bedrag1);
        allBedragen.add(bedrag2);
        allBedragen.add(bedrag3);
        allBedragen.add(bedrag4);
        allBedragen.add(bedrag5);
        
        List<Groepen> mainGroups = groepenSrv.findAllHoofdGroepen(allBedragen);
        List<Groepen> expectedMainGroups = new ArrayList<Groepen>();
        expectedMainGroups.add(groep1);
        expectedMainGroups.add(groep2);
        
        assertEquals(expectedMainGroups, mainGroups);
    }
}
