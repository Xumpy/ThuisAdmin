/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.dao.BedragenDaoImpl;
import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragenTotal;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import org.mockito.Spy;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class BedragenSrvTest {
    private static final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
    
    @Mock BedragenDaoImpl bedragenDao;
    
    @Mock Bedragen bedrag1;
    @Mock Bedragen bedrag2;
    @Mock Bedragen bedrag3;
    @Mock Groepen groepNegatief;
    @Mock Groepen groepPositief;
    
    @Mock Rekeningen rekening1;
    @Mock Rekeningen rekening2;
    
    @Spy OverzichtGroepBedragenTotal overzichtGroepBedragenTotal = new OverzichtGroepBedragenTotal();
    @InjectMocks BedragenSrvImpl bedragenSrv;
    
    List<Bedragen> bedragen = new ArrayList<Bedragen>();
    
    @Before
    public void SetUp() throws ParseException{
        bedragen.add(bedrag1);
        bedragen.add(bedrag2);
        bedragen.add(bedrag3);
        when(bedrag1.getRekening()).thenReturn(rekening1);
        when(bedrag2.getRekening()).thenReturn(rekening1);
        when(bedrag3.getRekening()).thenReturn(rekening1);
        when(bedrag1.getGroep()).thenReturn(groepNegatief);
        when(bedrag2.getGroep()).thenReturn(groepNegatief);
        when(bedrag3.getGroep()).thenReturn(groepPositief);
        when(groepNegatief.getNegatief()).thenReturn(1);
        when(groepPositief.getNegatief()).thenReturn(0);
        
        when(bedrag1.getDatum()).thenReturn(dt.parse("2015-02-18"));
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag1.getGroep()).thenReturn(groepNegatief);
        
        when(bedrag2.getDatum()).thenReturn(dt.parse("2015-02-19"));
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal(150));
        when(bedrag2.getGroep()).thenReturn(groepNegatief);
        
        when(bedrag3.getDatum()).thenReturn(dt.parse("2015-02-20"));
        when(bedrag3.getBedrag()).thenReturn(new BigDecimal(2000));
        when(bedrag3.getGroep()).thenReturn(groepPositief);
    }
    
    @Test
    public void testGetTotalRekeningBedragen() throws ParseException{
        when(rekening1.getWaarde()).thenReturn(new BigDecimal(300));
        when(rekening2.getWaarde()).thenReturn(new BigDecimal(300));
        when(bedrag3.getRekening()).thenReturn(rekening2);
        
        BigDecimal totalRekeningBedragen = bedragenSrv.getTotalRekeningBedragen(bedragen);
        
        assertEquals(totalRekeningBedragen, new BigDecimal(600));
    }
    
    @Test
    public void testIsRekeningUniqueTrue() throws ParseException{
        assertEquals(bedragenSrv.isRekeningUnique(bedragen), true);
    }
    
    @Test
    public void testIsRekeningUniqueFalse() throws ParseException{
        when(bedrag2.getRekening()).thenReturn(rekening2);
        assertEquals(bedragenSrv.isRekeningUnique(bedragen), false);
    }
    
    @Test
    public void testOverviewRekeningData() throws ParseException{
        Map overviewRekeningTestData = new LinkedHashMap();
        overviewRekeningTestData.put(dt.parse("2015-02-18"), new BigDecimal(450));
        overviewRekeningTestData.put(dt.parse("2015-02-19"), new BigDecimal(300));
        overviewRekeningTestData.put(dt.parse("2015-02-20"), new BigDecimal(2300));
        
        when(bedragenDao.getBedragAtDate(bedrag1.getDatum(), bedrag1.getRekening())).thenReturn(new BigDecimal(450));
        
        Map overviewRekeningData = bedragenSrv.OverviewRekeningData(bedragen);
        
        assertEquals(overviewRekeningTestData, overviewRekeningData);
    }
    
    
    @Test
    public void testOverviewRekeningGroep() throws ParseException{
        Map overviewRekeningTestData = new LinkedHashMap();
        
        Map bedrag = new LinkedHashMap();
        bedrag.put("POSITIEF", new BigDecimal(0));
        bedrag.put("NEGATIEF", new BigDecimal(570));
        overviewRekeningTestData.put(groepNegatief, bedrag);
                
        bedrag = new LinkedHashMap();
        bedrag.put("NEGATIEF", new BigDecimal(0));
        bedrag.put("POSITIEF", new BigDecimal(2000));
        overviewRekeningTestData.put(groepPositief, bedrag);
                
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(500));
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal(70));
        
        when(bedrag3.getGroep()).thenReturn(groepPositief);
        when(bedrag3.getBedrag()).thenReturn(new BigDecimal(2000));
        
        Map overviewRekeningData = bedragenSrv.OverviewRekeningGroep(bedragen);
        
        assertEquals(overviewRekeningTestData, overviewRekeningData);
    }
    
    @Test
    public void testBedragenInGroep() throws ParseException{
        List<Bedragen> bedragNegatief = new ArrayList<Bedragen>();
        List<Bedragen> bedragPositief = new ArrayList<Bedragen>();
        
        when(bedrag3.getGroep()).thenReturn(groepPositief);
        
        bedragNegatief.add(bedrag1);
        bedragNegatief.add(bedrag2);
        bedragPositief.add(bedrag3);
        
        assertEquals(bedragNegatief, bedragenSrv.getBedragenInGroep(bedragen, 
                                                                    groepNegatief, 
                                                                    groepNegatief.getNegatief()));
        
        assertEquals(bedragPositief, bedragenSrv.getBedragenInGroep(bedragen, 
                                                                    groepPositief, 
                                                                    groepPositief.getNegatief()));
    }
    
    @Test
    public void testFilterOverzichtGroepBedragenTotalType(){
        when(groepNegatief.getNaam()).thenReturn("Negatief");
        when(groepPositief.getNaam()).thenReturn("Positief");

        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal(200));
        when(bedrag3.getBedrag()).thenReturn(new BigDecimal(1000));
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTest = new OverzichtGroepBedragenTotal();
        
        List<OverzichtGroepBedragen> overzichtGroepBedragen = new ArrayList<OverzichtGroepBedragen>();
        List<OverzichtGroepBedragen> overzichtGroepBedragenTest = new ArrayList<OverzichtGroepBedragen>();
        
        
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag1));
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag2));
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag3));
        
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag1));
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag2));
        overzichtGroepBedragenTotalTest.setOverzichtGroepBedragen(overzichtGroepBedragenTest);
        
        overzichtGroepBedragenTotal.setSomBedrag(new BigDecimal(1300));
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTestOutput = bedragenSrv.filterOverzichtGroepBedragenTotalGroep(groepNegatief);
        
        assertEquals(new BigDecimal(300), overzichtGroepBedragenTotalTestOutput.getSomBedrag());
        assertEquals(overzichtGroepBedragenTotalTest.getOverzichtGroepBedragen(), overzichtGroepBedragenTotalTestOutput.getOverzichtGroepBedragen());
    }
    
    @Test
    public void testFilterOverzichtGroepBedragenTotalFilterBedrag(){
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal(200));
        when(bedrag3.getBedrag()).thenReturn(new BigDecimal(1000));
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTest = new OverzichtGroepBedragenTotal();
        
        List<OverzichtGroepBedragen> overzichtGroepBedragen = new ArrayList<OverzichtGroepBedragen>();
        List<OverzichtGroepBedragen> overzichtGroepBedragenTest = new ArrayList<OverzichtGroepBedragen>();
        
        
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag1));
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag2));
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag3));
        
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag1));
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag3));
        overzichtGroepBedragenTotalTest.setOverzichtGroepBedragen(overzichtGroepBedragenTest);
        
        overzichtGroepBedragenTotal.setSomBedrag(new BigDecimal(1300));
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTestOutput = bedragenSrv.filterOverzichtGroepBedragenTotalFilter("100");
        
        assertEquals(new BigDecimal(1100), overzichtGroepBedragenTotalTestOutput.getSomBedrag());
        assertEquals(overzichtGroepBedragenTotalTest.getOverzichtGroepBedragen(), overzichtGroepBedragenTotalTestOutput.getOverzichtGroepBedragen());
    }
    
    @Test
    public void testFilterOverzichtGroepBedragenTotalFilterOmschrijving(){
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal(200));
        when(bedrag3.getBedrag()).thenReturn(new BigDecimal(1000));
        when(bedrag1.getOmschrijving()).thenReturn("Peer");
        when(bedrag2.getOmschrijving()).thenReturn("Banaan");
        when(bedrag3.getOmschrijving()).thenReturn("Zoek de banaan");
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTest = new OverzichtGroepBedragenTotal();
        
        List<OverzichtGroepBedragen> overzichtGroepBedragen = new ArrayList<OverzichtGroepBedragen>();
        List<OverzichtGroepBedragen> overzichtGroepBedragenTest = new ArrayList<OverzichtGroepBedragen>();
        
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag1));
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag2));
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag3));
        
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag2));
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag3));
        overzichtGroepBedragenTotalTest.setOverzichtGroepBedragen(overzichtGroepBedragenTest);
        
        overzichtGroepBedragenTotal.setSomBedrag(new BigDecimal(1300));
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);

        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTestOutput = bedragenSrv.filterOverzichtGroepBedragenTotalFilter("banaan");
        
        assertEquals(new BigDecimal(1200), overzichtGroepBedragenTotalTestOutput.getSomBedrag());
        assertEquals(overzichtGroepBedragenTotalTest.getOverzichtGroepBedragen(), overzichtGroepBedragenTotalTestOutput.getOverzichtGroepBedragen());
    }
    
    @Test
    public void testFilterOverzichtGroepBedragenTotalFilterDatum(){
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal(200));
        when(bedrag3.getBedrag()).thenReturn(new BigDecimal(1000));
        when(bedrag1.getOmschrijving()).thenReturn("12-01-2015");
        when(bedrag2.getOmschrijving()).thenReturn("12-01-2015");
        when(bedrag3.getOmschrijving()).thenReturn("13-01-2015");
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTest = new OverzichtGroepBedragenTotal();
        
        List<OverzichtGroepBedragen> overzichtGroepBedragen = new ArrayList<OverzichtGroepBedragen>();
        List<OverzichtGroepBedragen> overzichtGroepBedragenTest = new ArrayList<OverzichtGroepBedragen>();
        
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag1));
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag2));
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag3));
        
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag1));
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag2));
        overzichtGroepBedragenTotalTest.setOverzichtGroepBedragen(overzichtGroepBedragenTest);
        
        overzichtGroepBedragenTotal.setSomBedrag(new BigDecimal(1300));
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);

        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTestOutput = bedragenSrv.filterOverzichtGroepBedragenTotalFilter("12-01-2015");
        
        assertEquals(new BigDecimal(300), overzichtGroepBedragenTotalTestOutput.getSomBedrag());
        assertEquals(overzichtGroepBedragenTotalTest.getOverzichtGroepBedragen(), overzichtGroepBedragenTotalTestOutput.getOverzichtGroepBedragen());
    }
    
    @Test
    public void testFilterOverzichtGroepBedragenTotalFilterNull(){
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal(200));
        when(bedrag3.getBedrag()).thenReturn(new BigDecimal(1000));
        when(bedrag1.getOmschrijving()).thenReturn("12-01-2015");
        when(bedrag2.getOmschrijving()).thenReturn("12-01-2015");
        when(bedrag3.getOmschrijving()).thenReturn("13-01-2015");
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTest = new OverzichtGroepBedragenTotal();
        
        List<OverzichtGroepBedragen> overzichtGroepBedragen = new ArrayList<OverzichtGroepBedragen>();
        List<OverzichtGroepBedragen> overzichtGroepBedragenTest = new ArrayList<OverzichtGroepBedragen>();
        
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag1));
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag2));
        overzichtGroepBedragen.add(new OverzichtGroepBedragen(bedrag3));
        
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag1));
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag2));
        overzichtGroepBedragenTest.add(new OverzichtGroepBedragen(bedrag3));
        overzichtGroepBedragenTotalTest.setOverzichtGroepBedragen(overzichtGroepBedragenTest);
        
        overzichtGroepBedragenTotal.setSomBedrag(new BigDecimal(1300));
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);

        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTestOutput = bedragenSrv.filterOverzichtGroepBedragenTotalFilter("");
        
        assertEquals(new BigDecimal(1300), overzichtGroepBedragenTotalTestOutput.getSomBedrag());
        assertEquals(overzichtGroepBedragenTotalTest.getOverzichtGroepBedragen(), overzichtGroepBedragenTotalTestOutput.getOverzichtGroepBedragen());
    }
}

