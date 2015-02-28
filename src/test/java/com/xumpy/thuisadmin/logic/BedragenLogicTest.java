/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.logic;

import com.xumpy.thuisadmin.dao.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.H2InMemory;
import com.xumpy.thuisadmin.dao.RekeningenDaoImpl;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.NieuwBedrag;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Nico
 */

@RunWith(MockitoJUnitRunner.class)
public class BedragenLogicTest{
    
    @Mock NieuwBedrag nieuwBedragMock;
    @Mock BedragenDaoImpl bedragenDaoMock;
    @Mock Bedragen bedragMock;
    @Mock Groepen groepMock;
    
    @Mock Bedragen oldBedragMock;
    @Mock Groepen oldGroepMock;
    
    @Mock BedragenDaoImpl bedragenDao;
    @Mock RekeningenDaoImpl rekeningenDao;
    
    @InjectMocks BedragenLogic bedragenLogic;
    Rekeningen rekening;
    
    @Before
    public void setUp2(){
        rekening = new Rekeningen();
        rekening.setWaarde(new BigDecimal("2000"));
        
        when(bedragMock.getRekening()).thenReturn(rekening);
        when(bedragMock.getGroep()).thenReturn(groepMock);
        
        when(bedragMock.getPk_id()).thenReturn(1);
        
        when(oldBedragMock.getGroep()).thenReturn(oldGroepMock);
        when(oldBedragMock.getRekening()).thenReturn(rekening);
        when(oldBedragMock.getBedrag()).thenReturn(new BigDecimal("400"));
        when(oldGroepMock.getNegatief()).thenReturn(1);
        
        when(bedragenDao.getBedrag(1)).thenReturn(oldBedragMock);
    }
    
    @Test
    public void testConvertNieuwBedragComma(){
        when(nieuwBedragMock.getBedrag()).thenReturn("5,4");
        Bedragen bedrag = bedragenLogic.convertNieuwBedrag(nieuwBedragMock);
        assertEquals(new BigDecimal("5.40"), bedrag.getBedrag());
    }
    
    @Test
    public void testConvertNieuwBedragPoint(){
        when(nieuwBedragMock.getBedrag()).thenReturn("5.4");
        Bedragen bedrag = bedragenLogic.convertNieuwBedrag(nieuwBedragMock);
        assertEquals(new BigDecimal("5.40"), bedrag.getBedrag());
    }
    
    
    @Test
    public void testConvertNieuwBedrag1000A(){
        when(nieuwBedragMock.getBedrag()).thenReturn("1.456,45");
        Bedragen bedrag = bedragenLogic.convertNieuwBedrag(nieuwBedragMock);
        assertEquals(new BigDecimal("1456.45"), bedrag.getBedrag());
    }
    
    @Test
    public void testConvertNieuwBedrag1000B(){
        when(nieuwBedragMock.getBedrag()).thenReturn("1456.45");
        Bedragen bedrag = bedragenLogic.convertNieuwBedrag(nieuwBedragMock);
        assertEquals(new BigDecimal("1456.45"), bedrag.getBedrag());
    }
    
    @Test
    public void testConvertNieuwBedrag1000C(){
        when(nieuwBedragMock.getBedrag()).thenReturn("2000");
        
        Bedragen bedrag = bedragenLogic.convertNieuwBedrag(nieuwBedragMock);
        assertEquals(new BigDecimal("2000.00"), bedrag.getBedrag()); 
    }
   
    @Test
    public void testProcessRekeningBedrag1(){
        when(bedragMock.getBedrag()).thenReturn(new BigDecimal("800"));
        when(groepMock.getNegatief()).thenReturn(1);
        
        Bedragen bedrag = bedragenLogic.processRekeningBedrag(bedragMock, bedragenLogic.INSERT);
        
        assertEquals(new BigDecimal("1200"), bedrag.getRekening().getWaarde());
    }
    
    @Test
    public void testProcessRekeningBedrag2(){
        when(bedragMock.getBedrag()).thenReturn(new BigDecimal("800"));
        when(groepMock.getNegatief()).thenReturn(1);
        
        Bedragen oldBedrag = new Bedragen(); 
        oldBedrag.setRekening(rekening);
        
        Bedragen bedrag = bedragenLogic.processRekeningBedrag(bedragMock, bedragenLogic.UPDATE);
        
        assertEquals(new BigDecimal("1600"), bedrag.getRekening().getWaarde());
    }
    
    @Test
    public void testProcessRekeningBedrag3(){
       when(bedragMock.getBedrag()).thenReturn(new BigDecimal("2000"));
       when(groepMock.getNegatief()).thenReturn(0);
        
       Bedragen bedrag = bedragenLogic.processRekeningBedrag(bedragMock, bedragenLogic.INSERT);
       
       assertEquals(new BigDecimal("4000"), bedrag.getRekening().getWaarde());
    }
    
    @Test
    public void testProcessRekeningBedrag4(){
       when(bedragMock.getBedrag()).thenReturn(new BigDecimal("500"));
       when(groepMock.getNegatief()).thenReturn(0);
       
       Bedragen bedrag = bedragenLogic.processRekeningBedrag(bedragMock, bedragenLogic.UPDATE);
       
       assertEquals(new BigDecimal("2900"), bedrag.getRekening().getWaarde());
    }
    
    @Test
    public void testProcessRekeningBedragDelete(){
        when(bedragMock.getBedrag()).thenReturn(new BigDecimal("500"));
        when(groepMock.getNegatief()).thenReturn(0);
        Bedragen bedrag = bedragenLogic.processRekeningBedrag(bedragMock, bedragenLogic.DELETE);
        assertEquals(new BigDecimal("1500"), bedrag.getRekening().getWaarde());
    }
    
    @Test
    public void testProcessRekeningBedragDelete2(){
        when(bedragMock.getBedrag()).thenReturn(new BigDecimal("500"));
        when(groepMock.getNegatief()).thenReturn(1);
        Bedragen bedrag = bedragenLogic.processRekeningBedrag(bedragMock, bedragenLogic.DELETE);
        assertEquals(new BigDecimal("2500"), bedrag.getRekening().getWaarde());
    }
    
    @Test
    public void testMoveBedragFromOneRekeningToAnother(){
        Rekeningen newRekening = new Rekeningen();
        newRekening.setPk_id(2);
        newRekening.setWaarde(new BigDecimal(2000));
        
        when(bedragMock.getBedrag()).thenReturn(new BigDecimal("500"));
        when(rekeningenDao.findRekening(2)).thenReturn(newRekening);
        
        newRekening = bedragenLogic.moveBedragToRekening(bedragMock, newRekening);
        
        assertEquals(new BigDecimal(1500), bedragMock.getRekening().getWaarde());
        assertEquals(new BigDecimal(2500), newRekening.getWaarde());
    }
}
