/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services;

import com.xumpy.thuisadmin.controllers.model.BeheerBedragenInp;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenReport;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenReportLst;
import com.xumpy.thuisadmin.controllers.model.FinanceOverzichtGroep;
import com.xumpy.thuisadmin.controllers.model.NieuwBedrag;
import com.xumpy.thuisadmin.services.implementations.BedragenSrvImpl;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.controllers.model.RekeningOverzicht;
import com.xumpy.thuisadmin.dao.implementations.GroepenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import com.xumpy.thuisadmin.services.model.GroepenSrvPojo;
import com.xumpy.thuisadmin.services.model.RekeningenSrvPojo;
import com.xumpy.security.model.UserInfo;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class BedragenSrvTest{
    private static final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
    
    @Mock RekeningenDaoImpl rekeningenDao;
    @Mock BedragenDaoImpl bedragenDao;
    @Mock GroepenDaoImpl groepenDao;

    @Spy BeheerBedragenReportLst beheerBedragenReportLst;
    @Spy BeheerBedragenInp beheerBedragenInp;
    @Mock BeheerBedragenReport beheerBedragenReport;    
    
    @Mock OverzichtGroepBedragenTotal overzichtGroepBedragenTotal;
    @Mock BedragenDaoPojo bedrag1;
    @Mock BedragenDaoPojo bedrag2;
    @Mock BedragenDaoPojo bedrag3;
    @Mock BedragenDaoPojo bedrag4;
    @Mock BedragenDaoPojo bedrag5;
    
    @Mock GroepenDaoPojo mainGroup;
    @Mock GroepenDaoPojo groepNegatief;
    @Mock GroepenDaoPojo groepPositief;
    
    @Mock RekeningenDaoPojo rekening1;
    @Mock RekeningenDaoPojo rekening2;

    @Mock PersonenDaoPojo persoon;
    @Mock UserInfo userInfo;
    
    @InjectMocks BedragenSrvImpl bedragenSrv;
    
    @Before
    public void SetUp() throws ParseException{
        when(groepNegatief.getPk_id()).thenReturn(1);
        when(groepPositief.getPk_id()).thenReturn(2);
        
        when(bedrag1.getDatum()).thenReturn(dt.parse("2015-02-18"));
        when(bedrag1.getRekening()).thenReturn(rekening1);
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag1.getGroep()).thenReturn(groepNegatief);
        when(bedrag1.getPersoon()).thenReturn(persoon);
        
        when(bedrag2.getDatum()).thenReturn(dt.parse("2015-02-19"));
        when(bedrag2.getRekening()).thenReturn(rekening1);
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal(150));
        when(bedrag2.getGroep()).thenReturn(groepNegatief);
        when(bedrag2.getPersoon()).thenReturn(persoon);
        
        when(bedrag3.getDatum()).thenReturn(dt.parse("2015-02-20"));
        when(bedrag3.getRekening()).thenReturn(rekening1);
        when(bedrag3.getBedrag()).thenReturn(new BigDecimal(2000));
        when(bedrag3.getGroep()).thenReturn(groepPositief);
        when(bedrag3.getPersoon()).thenReturn(persoon);
        
        when(bedrag4.getDatum()).thenReturn(dt.parse("2015-02-19"));
        when(bedrag3.getRekening()).thenReturn(rekening2);
        when(bedrag4.getBedrag()).thenReturn(new BigDecimal(150));
        when(bedrag4.getGroep()).thenReturn(groepNegatief);
        when(bedrag4.getPersoon()).thenReturn(persoon);
        
        when(bedrag5.getDatum()).thenReturn(dt.parse("2015-02-20"));
        when(bedrag3.getRekening()).thenReturn(rekening2);
        when(bedrag5.getBedrag()).thenReturn(new BigDecimal(2000));
        when(bedrag5.getGroep()).thenReturn(groepPositief);
        when(bedrag5.getPersoon()).thenReturn(persoon);
        
        when(groepNegatief.getNaam()).thenReturn("Groep A");
        when(groepNegatief.getNegatief()).thenReturn(1);
        when(groepPositief.getNaam()).thenReturn("Groep B");
        when(groepPositief.getNegatief()).thenReturn(0);
        
        when(userInfo.getPersoon()).thenReturn(persoon);
    }
    
    @Test
    public void testSave(){
        NieuwBedrag nieuwBedrag = Mockito.mock(NieuwBedrag.class);
        
        when(rekening1.getWaarde()).thenReturn(new BigDecimal(2000));
        RekeningenSrvPojo rekeningSrvPojo = new RekeningenSrvPojo(rekening1);
        GroepenSrvPojo groepPositiefSrvPojo = new GroepenSrvPojo(groepPositief);
        
        when(nieuwBedrag.getPk_id()).thenReturn(null);
        when(nieuwBedrag.getRekening()).thenReturn(rekeningSrvPojo);
        when(nieuwBedrag.getBedrag()).thenReturn("200");
        when(nieuwBedrag.getGroep()).thenReturn(groepPositiefSrvPojo);
        when(bedragenDao.getNewPkId()).thenReturn(1);
        
        Bedragen bedragTest = bedragenSrv.save(nieuwBedrag);
        
        assertEquals(new Integer(1), bedragTest.getPk_id());
        assertEquals(new BigDecimal("2200.00"), bedragTest.getRekening().getWaarde());
    }
    
    @Test
    public void testUpdate(){
        NieuwBedrag nieuwBedrag = Mockito.mock(NieuwBedrag.class);
        when(rekening1.getWaarde()).thenReturn(new BigDecimal(2000));
        RekeningenSrvPojo rekeningSrvPojo = new RekeningenSrvPojo(rekening1);
        GroepenSrvPojo groepPositiefSrvPojo = new GroepenSrvPojo(groepPositief);
        
        when(nieuwBedrag.getPk_id()).thenReturn(1);
        when(nieuwBedrag.getRekening()).thenReturn(rekeningSrvPojo);
        when(nieuwBedrag.getBedrag()).thenReturn("200");
        when(nieuwBedrag.getGroep()).thenReturn(groepPositiefSrvPojo);
        
        when(bedragenSrv.findBedrag(1)).thenReturn(bedrag2); // This means previous bedrag was 150
        
        Bedragen bedragTest = bedragenSrv.save(nieuwBedrag);
        
        assertEquals(new BigDecimal("2350.00"), bedragTest.getRekening().getWaarde());
    }
    
    @Test
    public void testDelete(){
        NieuwBedrag nieuwBedrag = Mockito.mock(NieuwBedrag.class);
        when(rekening1.getWaarde()).thenReturn(new BigDecimal(2000));
        RekeningenSrvPojo rekeningSrvPojo = new RekeningenSrvPojo(rekening1);
        GroepenSrvPojo groepPositiefSrvPojo = new GroepenSrvPojo(groepPositief);
        
        when(nieuwBedrag.getPk_id()).thenReturn(1);
        when(nieuwBedrag.getRekening()).thenReturn(rekeningSrvPojo);
        when(nieuwBedrag.getBedrag()).thenReturn("200");
        when(nieuwBedrag.getGroep()).thenReturn(groepPositiefSrvPojo);
        
        Bedragen bedragTest = bedragenSrv.delete(nieuwBedrag);
        
        assertEquals(bedragTest.getPk_id(), bedragenSrv.convertNieuwBedrag(nieuwBedrag).getPk_id());
    }
    
    @Test
    public void testGetTotalRekeningBedragen() throws ParseException{
        List<Bedragen> bedragen = new ArrayList<Bedragen>();
        bedragen.add(bedrag1);
        bedragen.add(bedrag2);
        bedragen.add(bedrag3);
        when(rekening1.getWaarde()).thenReturn(new BigDecimal(300));
        when(rekening2.getWaarde()).thenReturn(new BigDecimal(300));
        when(bedrag3.getRekening()).thenReturn(rekening2);
        
        BigDecimal totalRekeningBedragen = bedragenSrv.getTotalRekeningBedragen(bedragen);
        
        assertEquals(totalRekeningBedragen, new BigDecimal(600));
    }
    
  
    @Test
    public void testIsRekeningUniqueTrue() throws ParseException{
        RekeningenDaoPojo rekening = new RekeningenDaoPojo();
        RekeningenDaoPojo rekening2 = new RekeningenDaoPojo();
        rekening.setPk_id(1);
        rekening2.setPk_id(1);
        
        List<Bedragen> bedragen = new ArrayList<Bedragen>();
        bedragen.add(bedrag1);
        bedragen.add(bedrag2);
        bedragen.add(bedrag3);
        
        when(bedrag1.getRekening()).thenReturn(rekening);
        when(bedrag2.getRekening()).thenReturn(rekening);
        when(bedrag3.getRekening()).thenReturn(rekening);
        
        assertEquals(true, bedragenSrv.isRekeningUnique(bedragen));
    }
    
    @Test
    public void testIsRekeningUniqueFalse() throws ParseException{
        RekeningenSrvPojo rekening = new RekeningenSrvPojo();
        RekeningenSrvPojo rekening2 = new RekeningenSrvPojo();
        rekening.setPk_id(1);
        rekening2.setPk_id(2);
        
        List<Bedragen> bedragen = new ArrayList<Bedragen>();
        bedragen.add(bedrag1);
        bedragen.add(bedrag2);
        bedragen.add(bedrag3);
        
        when(bedrag1.getRekening()).thenReturn(new RekeningenDaoPojo(rekening));
        when(bedrag2.getRekening()).thenReturn(new RekeningenDaoPojo(rekening2));
        when(bedrag3.getRekening()).thenReturn(new RekeningenDaoPojo(rekening));
        
        assertEquals(false, bedragenSrv.isRekeningUnique(bedragen));
    }

    @Test
    public void testOverviewRekeningGroep() throws ParseException{
        List<Bedragen> bedragen = new ArrayList<Bedragen>();
        bedragen.add(bedrag1);
        bedragen.add(bedrag2);
        bedragen.add(bedrag3);
        
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
        List<Bedragen> bedragen = new ArrayList<Bedragen>();
        bedragen.add(bedrag1);
        bedragen.add(bedrag2);
        bedragen.add(bedrag3);
        
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
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotal = new OverzichtGroepBedragenTotal();
        overzichtGroepBedragenTotal.setSomBedrag(new BigDecimal(1300));
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTestOutput = bedragenSrv.filterOverzichtGroepBedragenTotalGroep(overzichtGroepBedragenTotal, groepNegatief);
        
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
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotal = new OverzichtGroepBedragenTotal();
        overzichtGroepBedragenTotal.setSomBedrag(new BigDecimal(1300));
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTestOutput = bedragenSrv.filterOverzichtGroepBedragenTotalFilter(overzichtGroepBedragenTotal, "100");
        
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
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotal = new OverzichtGroepBedragenTotal();
        overzichtGroepBedragenTotal.setSomBedrag(new BigDecimal(1300));
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);

        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTestOutput = bedragenSrv.filterOverzichtGroepBedragenTotalFilter(overzichtGroepBedragenTotal, "banaan");
        
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
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotal = new OverzichtGroepBedragenTotal();
        overzichtGroepBedragenTotal.setSomBedrag(new BigDecimal(1300));
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);

        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTestOutput = bedragenSrv.filterOverzichtGroepBedragenTotalFilter(overzichtGroepBedragenTotal, "12-01-2015");
        
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
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTotal = new OverzichtGroepBedragenTotal();
        overzichtGroepBedragenTotal.setSomBedrag(new BigDecimal(1300));
        overzichtGroepBedragenTotal.setOverzichtGroepBedragen(overzichtGroepBedragen);

        OverzichtGroepBedragenTotal overzichtGroepBedragenTotalTestOutput = bedragenSrv.filterOverzichtGroepBedragenTotalFilter(overzichtGroepBedragenTotal, "");
        
        assertEquals(new BigDecimal(1300), overzichtGroepBedragenTotalTestOutput.getSomBedrag());
        assertEquals(overzichtGroepBedragenTotalTest.getOverzichtGroepBedragen(), overzichtGroepBedragenTotalTestOutput.getOverzichtGroepBedragen());
    }
    
    @Test
    public void testOrderByGroup(){
        List<Bedragen> bedragen = new ArrayList<Bedragen>();
        bedragen.add(bedrag1);
        bedragen.add(bedrag2);
        bedragen.add(bedrag3);
        
        when(bedrag1.getGroep()).thenReturn(groepNegatief);
        when(bedrag2.getGroep()).thenReturn(groepPositief);
        when(bedrag3.getGroep()).thenReturn(groepNegatief);

        List<Bedragen> orderByBedragen = new ArrayList<Bedragen>();
        orderByBedragen.add(bedrag1);
        orderByBedragen.add(bedrag3);
        orderByBedragen.add(bedrag2);
        
        assertEquals(orderByBedragen, bedragenSrv.orderByGroup(bedragen));
    }

    @Test
    public void testOverviewRekeningData() throws ParseException{
        List<Bedragen> bedragen = new ArrayList<Bedragen>();
        bedragen.add(bedrag1);
        bedragen.add(bedrag2);
        bedragen.add(bedrag3);
        
        Map overviewRekeningTestData = new LinkedHashMap();
        overviewRekeningTestData.put(dt.parse("2015-02-18"), new BigDecimal(450));
        overviewRekeningTestData.put(dt.parse("2015-02-19"), new BigDecimal(300));
        overviewRekeningTestData.put(dt.parse("2015-02-20"), new BigDecimal(2300));
        
        when(bedragenSrv.getBedragAtDate(bedrag1.getDatum(), null)).thenReturn(new BigDecimal(450));
        
        Map overviewRekeningData = bedragenSrv.OverviewRekeningData(bedragen);
        
        assertEquals(overviewRekeningTestData, overviewRekeningData);
    }
    
    @Test
    public void testFindBedrag(){
        when(bedragenSrv.findBedrag(1)).thenReturn(bedrag1);
        
        assertEquals(bedrag1, bedragenSrv.findBedrag(1));
    }
    
    @Test
    public void testBeheerBedragenReportLst(){
        BeheerBedragenReportLst beheerBedragen = new BeheerBedragenReportLst();
        
        when(bedrag1.getPk_id()).thenReturn(1);
        when(bedrag2.getPk_id()).thenReturn(2);
        when(bedrag3.getPk_id()).thenReturn(3);
        
        List<BedragenDaoPojo> lstBedragen = new ArrayList<BedragenDaoPojo>();
        lstBedragen.add(new BedragenDaoPojo(bedrag1));
        lstBedragen.add(new BedragenDaoPojo(bedrag2));
        lstBedragen.add(new BedragenDaoPojo(bedrag3));
        
        Pageable topTen = new PageRequest(1, 10); 
        
        when(bedragenDao.reportBedragen(rekening1.getPk_id(), null, userInfo.getPersoon().getPk_id(), topTen)).thenReturn(lstBedragen);
        
        beheerBedragen = bedragenSrv.reportBedragen(beheerBedragen, 1, rekening1, null);
        
        assertEquals(lstBedragen.get(0).getPk_id(), beheerBedragen.getBeheerBedragenReport().get(0).getPk_id());
        assertEquals(lstBedragen.get(1).getPk_id(), beheerBedragen.getBeheerBedragenReport().get(1).getPk_id());
        assertEquals(lstBedragen.get(2).getPk_id(), beheerBedragen.getBeheerBedragenReport().get(2).getPk_id());
    }
    
    @Test
    public void testGraphiekOverzichtGroep() throws ParseException{
        List<BedragenDaoPojo> lstBedragen = new ArrayList<BedragenDaoPojo>();
        lstBedragen.add(new BedragenDaoPojo(bedrag1));
        lstBedragen.add(new BedragenDaoPojo(bedrag2));
        lstBedragen.add(new BedragenDaoPojo(bedrag3));
        
        when(bedragenDao.BedragInPeriode(dt.parse("2015-02-18"), dt.parse("2015-02-23"), null, 1, userInfo.getPersoon().getPk_id())).thenReturn(lstBedragen);
        
        FinanceOverzichtGroep financeOverzichtGroep = bedragenSrv.graphiekOverzichtGroep(dt.parse("2015-02-18"), dt.parse("2015-02-23"), 1);
        
        assertEquals(new Double("250.00"), financeOverzichtGroep.getTotaal_kosten());
        assertEquals(new Double("2000.00"), financeOverzichtGroep.getTotaal_opbrengsten());
    }
    
    @Test
    public void testGraphiekOverzichtGroepFilterInterRekening() throws ParseException{
        when(groepPositief.getCodeId()).thenReturn("INTER_REKENING");
        
        List<BedragenDaoPojo> lstBedragen = new ArrayList<BedragenDaoPojo>();
        lstBedragen.add(new BedragenDaoPojo(bedrag1));
        lstBedragen.add(new BedragenDaoPojo(bedrag2));
        lstBedragen.add(new BedragenDaoPojo(bedrag3));
        
        when(bedragenDao.BedragInPeriode(dt.parse("2015-02-18"), dt.parse("2015-02-23"), null, 1, userInfo.getPersoon().getPk_id())).thenReturn(lstBedragen);
        
        FinanceOverzichtGroep financeOverzichtGroep = bedragenSrv.graphiekOverzichtGroep(dt.parse("2015-02-18"), dt.parse("2015-02-23"), 1);
        
        assertEquals(new Double("250.00"), financeOverzichtGroep.getTotaal_kosten());
        assertEquals(new Double("0.0"), financeOverzichtGroep.getTotaal_opbrengsten());
    }
    
    @Test
    public void testGraphiekBedrag() throws ParseException{
        when(bedrag1.getRekening()).thenReturn(rekening1);
        when(bedrag1.getDatum()).thenReturn(dt.parse("2015-02-18"));
        when(bedrag2.getRekening()).thenReturn(rekening1);
        when(bedrag2.getDatum()).thenReturn(dt.parse("2015-02-19"));
        when(bedrag3.getRekening()).thenReturn(rekening1);
        when(bedrag3.getDatum()).thenReturn(dt.parse("2015-02-20"));
        
        List<BedragenDaoPojo> lstBedragen = new ArrayList<BedragenDaoPojo>();
        lstBedragen.add(bedrag1);
        lstBedragen.add(bedrag2);
        lstBedragen.add(bedrag3);
        
        when(bedragenDao.BedragInPeriode(dt.parse("2015-02-18"), dt.parse("2015-02-23"), rekening1.getPk_id(), 0, userInfo.getPersoon().getPk_id())).thenReturn(lstBedragen);
        when(rekening1.getWaarde()).thenReturn(new BigDecimal(2000));
        when(bedragenSrv.getBedragAtDate(bedrag1.getDatum(), bedrag1.getRekening())).thenReturn(new BigDecimal(2000));
        
        List<RekeningOverzicht> lstRekeningOverzicht = bedragenSrv.graphiekBedrag(rekening1, dt.parse("2015-02-18"), dt.parse("2015-02-23"));
        
        assertEquals(new BigDecimal(2000), lstRekeningOverzicht.get(0).getBedrag());
        assertEquals(dt.parse("2015-02-18"), lstRekeningOverzicht.get(0).getDatum());
        assertEquals(new BigDecimal(1850), lstRekeningOverzicht.get(1).getBedrag());
        assertEquals(dt.parse("2015-02-19"), lstRekeningOverzicht.get(1).getDatum());
        assertEquals(new BigDecimal(3850), lstRekeningOverzicht.get(2).getBedrag());
        assertEquals(dt.parse("2015-02-20"), lstRekeningOverzicht.get(2).getDatum());
    }
    
    @Test
    public void testConvertNieuwBedragComma(){
        NieuwBedrag nieuwBedrag = Mockito.mock(NieuwBedrag.class);
        when(nieuwBedrag.getBedrag()).thenReturn("5,40");
        Bedragen bedrag = bedragenSrv.convertNieuwBedrag(nieuwBedrag);
        assertEquals(new BigDecimal("5.40"), bedrag.getBedrag());
    }
    
    @Test
    public void testConvertNieuwBedragPoint(){
        NieuwBedrag nieuwBedrag = Mockito.mock(NieuwBedrag.class);
        when(nieuwBedrag.getBedrag()).thenReturn("5.40");
        Bedragen bedrag = bedragenSrv.convertNieuwBedrag(nieuwBedrag);
        assertEquals(new BigDecimal("5.40"), bedrag.getBedrag());
    }
    
    
    @Test
    public void testConvertNieuwBedrag1000A(){
        NieuwBedrag nieuwBedrag = Mockito.mock(NieuwBedrag.class);
        when(nieuwBedrag.getBedrag()).thenReturn("1.456,45");
        Bedragen bedrag = bedragenSrv.convertNieuwBedrag(nieuwBedrag);
        assertEquals(new BigDecimal("1456.45"), bedrag.getBedrag());
    }
    
    @Test
    public void testConvertNieuwBedrag1000B(){
        NieuwBedrag nieuwBedrag = Mockito.mock(NieuwBedrag.class);
        when(nieuwBedrag.getBedrag()).thenReturn("1456.45");
        Bedragen bedrag = bedragenSrv.convertNieuwBedrag(nieuwBedrag);
        assertEquals(new BigDecimal("1456.45"), bedrag.getBedrag());
    }
    
    @Test
    public void testConvertNieuwBedrag1000C(){
        NieuwBedrag nieuwBedrag = Mockito.mock(NieuwBedrag.class);
        when(nieuwBedrag.getBedrag()).thenReturn("2000");
        Bedragen bedrag = bedragenSrv.convertNieuwBedrag(nieuwBedrag);
        assertEquals(new BigDecimal("2000.00"), bedrag.getBedrag());
    }
   
    @Test
    public void testProcessRekeningBedragInsert(){
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal("800"));
        when(groepNegatief.getNegatief()).thenReturn(1);
        when(rekening1.getWaarde()).thenReturn(new BigDecimal("2000"));
        
        Bedragen bedrag = bedragenSrv.processRekeningBedrag(bedrag1, bedragenSrv.INSERT);
        
        assertEquals(new BigDecimal("1200"), bedrag.getRekening().getWaarde());
    }
    
    @Test
    public void testProcessRekeningBedragUpdate(){
        when(bedrag1.getPk_id()).thenReturn(1);
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal("800"));
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal("400"));
        when(groepNegatief.getNegatief()).thenReturn(1);
        when(rekening1.getWaarde()).thenReturn(new BigDecimal("2000"));
        
        when(bedragenDao.findOne(1)).thenReturn(bedrag2);
                
        Bedragen bedrag = bedragenSrv.processRekeningBedrag(bedrag1, bedragenSrv.UPDATE);
        
        assertEquals(new BigDecimal("1600"), bedrag.getRekening().getWaarde());
    }

    @Test
    public void testProcessRekeningBedragDelete(){
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal("500"));
        when(bedrag1.getGroep()).thenReturn(groepPositief);
        when(rekening1.getWaarde()).thenReturn(new BigDecimal("2000"));
        
        Bedragen bedrag = bedragenSrv.processRekeningBedrag(bedrag1, bedragenSrv.DELETE);
        assertEquals(new BigDecimal("1500"), bedrag.getRekening().getWaarde());
    }
    
    @Test
    public void testMoveBedragFromOneRekeningToAnother(){
        RekeningenSrvPojo newRekening = new RekeningenSrvPojo();
        newRekening.setPk_id(2);
        newRekening.setWaarde(new BigDecimal(1500));
        
        when(bedrag1.getRekening()).thenReturn(rekening1);
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(500));
        when(rekening1.getWaarde()).thenReturn(new BigDecimal(900));
        
        BedragenSrvPojo bedragSrvPojoMock = new BedragenSrvPojo(bedrag1);
        when(rekeningenDao.findOne(2)).thenReturn(new RekeningenDaoPojo(newRekening));
        
        newRekening = new RekeningenSrvPojo(bedragenSrv.moveBedragToRekening(bedragSrvPojoMock, newRekening));
        
        assertEquals(new BigDecimal(1400), bedragSrvPojoMock.getRekening().getWaarde());
        assertEquals(new BigDecimal(1000), newRekening.getWaarde());
    }
    
    public void mockSizeBeheerBedragenReportLst(Integer size){
        List<BeheerBedragenReport> lstBeheerBedragenReport = new ArrayList<BeheerBedragenReport>();
        for (int i=0; i<size; i++){
            lstBeheerBedragenReport.add(beheerBedragenReport);
        }
        when(beheerBedragenReportLst.getBeheerBedragenReport()).thenReturn(lstBeheerBedragenReport);
    }
    
    @Test
    public void testSetButtonsTestNoPreviousNoNext(){
        when(beheerBedragenInp.getOffset()).thenReturn(0);
        mockSizeBeheerBedragenReportLst(5);
        
        BeheerBedragenReportLst result = BedragenSrvImpl.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(result.isShowNext(), false);
        assertEquals(result.isShowPrevious(), false);
    }
    
    @Test
    public void testSetButtonsTestNoPreviousYesNext(){
        when(beheerBedragenInp.getOffset()).thenReturn(0);
        mockSizeBeheerBedragenReportLst(10);
        
        BeheerBedragenReportLst result = BedragenSrvImpl.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(result.isShowNext(), true);
        assertEquals(result.isShowPrevious(), false);
    }
    
    @Test
    public void testSetButtonsTestYesPreviousNoNext(){
        when(beheerBedragenInp.getOffset()).thenReturn(1);
        mockSizeBeheerBedragenReportLst(5);
        
        BeheerBedragenReportLst result = BedragenSrvImpl.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(result.isShowNext(), false);
        assertEquals(result.isShowPrevious(), true);
    }
    
    @Test
    public void testSetButtonsTestYesPreviousYesNext(){
        when(beheerBedragenInp.getOffset()).thenReturn(1);
        mockSizeBeheerBedragenReportLst(10);
        
        BeheerBedragenReportLst result = BedragenSrvImpl.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(result.isShowNext(), true);
        assertEquals(result.isShowPrevious(), true);
    }
    
    @Test
    public void testFindAllMonthsBedragen() throws ParseException{
        when(bedrag1.getDatum()).thenReturn(dt.parse("2015-02-19"));
        when(bedrag2.getDatum()).thenReturn(dt.parse("2015-04-19"));
        when(bedrag3.getDatum()).thenReturn(dt.parse("2015-02-20"));
        when(bedrag4.getDatum()).thenReturn(dt.parse("2015-02-19"));
        when(bedrag5.getDatum()).thenReturn(dt.parse("2015-04-20"));
        
        List<Bedragen> allBedragen = new ArrayList<Bedragen>();
        allBedragen.add(bedrag1);
        allBedragen.add(bedrag2);
        allBedragen.add(bedrag3);
        allBedragen.add(bedrag4);
        allBedragen.add(bedrag5);
        
        List<String> expectedDates = new ArrayList<String>();
        expectedDates.add("02/2015");
        expectedDates.add("04/2015");
        
        assertEquals(expectedDates, bedragenSrv.findAllMonthsBedragen(allBedragen));
    }
    
    @Test
    public void testFindMainBedragen() throws ParseException{
        GroepenDaoPojo mainGroup2 = Mockito.mock(GroepenDaoPojo.class);
        when(mainGroup.getPk_id()).thenReturn(1);
        when(mainGroup2.getPk_id()).thenReturn(2);
        when(groepNegatief.getHoofdGroep()).thenReturn(mainGroup);
        
        when(mainGroup.getNegatief()).thenReturn(1);
        when(mainGroup2.getNegatief()).thenReturn(0);
        when(groepNegatief.getNegatief()).thenReturn(1);
        
        when(bedrag1.getDatum()).thenReturn(dt.parse("2015-02-19"));
        when(bedrag1.getGroep()).thenReturn(mainGroup);
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag2.getDatum()).thenReturn(dt.parse("2015-04-19"));
        when(bedrag2.getGroep()).thenReturn(groepNegatief);
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal(50));
        when(bedrag3.getDatum()).thenReturn(dt.parse("2015-02-20"));
        when(bedrag3.getGroep()).thenReturn(groepNegatief);
        when(bedrag3.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag4.getDatum()).thenReturn(dt.parse("2015-02-19"));
        when(bedrag4.getGroep()).thenReturn(mainGroup2);
        when(bedrag4.getBedrag()).thenReturn(new BigDecimal(50));
        when(bedrag5.getDatum()).thenReturn(dt.parse("2015-04-20"));
        when(bedrag5.getGroep()).thenReturn(mainGroup);
        when(bedrag5.getBedrag()).thenReturn(new BigDecimal(100));
        
        List<Bedragen> allBedragen = new ArrayList<Bedragen>();
        allBedragen.add(bedrag1);
        allBedragen.add(bedrag2);
        allBedragen.add(bedrag3);
        allBedragen.add(bedrag4);
        allBedragen.add(bedrag5);
        
        String month="02/2015";
        Map<Integer, BigDecimal> expectedResult = new HashMap<Integer, BigDecimal>();
        expectedResult.put(0, new BigDecimal("75.00"));
        expectedResult.put(2, new BigDecimal("-50"));
        expectedResult.put(1, new BigDecimal("200"));
        
        assertEquals(expectedResult, bedragenSrv.findMainBedragen(allBedragen, month));
    }
    
    @Test
    public void testSelectBedragenInPeriode() throws ParseException{
        List<BedragenDaoPojo> allBedragen = new ArrayList<BedragenDaoPojo>();
        allBedragen.add(bedrag1);
        allBedragen.add(bedrag2);
        allBedragen.add(bedrag3);
        allBedragen.add(bedrag4);
        allBedragen.add(bedrag5);
        
        when(bedragenDao.BedragInPeriode(dt.parse("2015-02-01"), dt.parse("2015-01-31"), null, 0, userInfo.getPersoon().getPk_id())).thenReturn(allBedragen);
        
        assertEquals(allBedragen, bedragenSrv.selectBedragenInPeriode(dt.parse("2015-02-01"), dt.parse("2015-01-31")));
    }
    
    @Test
    public void filterBedragenWithMainGroup() throws ParseException{
        GroepenDaoPojo mainGroup2 = Mockito.mock(GroepenDaoPojo.class);
        GroepenDaoPojo mainGroup3 = Mockito.mock(GroepenDaoPojo.class);
        
        when(mainGroup.getPk_id()).thenReturn(1);
        when(mainGroup2.getPk_id()).thenReturn(2);
        when(mainGroup3.getPk_id()).thenReturn(3);
        
        
        List<Bedragen> allBedragen = new ArrayList<Bedragen>();
        when(bedrag1.getGroep()).thenReturn(mainGroup);
        when(bedrag2.getGroep()).thenReturn(mainGroup2);
        
        when(bedrag3.getGroep()).thenReturn(mainGroup3);
        when(bedrag4.getGroep()).thenReturn(mainGroup3);
        when(bedrag5.getGroep()).thenReturn(mainGroup3);
        
        allBedragen.add(bedrag1);
        allBedragen.add(bedrag2);
        allBedragen.add(bedrag3);
        allBedragen.add(bedrag4);
        allBedragen.add(bedrag5);
        
        List<Integer> mainGroup = new ArrayList<Integer>();
        mainGroup.add(1);
        mainGroup.add(2);
        
        List<Bedragen> expectedResult = new ArrayList<Bedragen>();
        expectedResult.add(bedrag1);
        expectedResult.add(bedrag2);
        
        assertEquals(expectedResult, bedragenSrv.filterBedragenWithMainGroup(allBedragen, mainGroup));
    }
    
    @Test
    public void testRapportOverzichtGroepBedragen() throws ParseException{
        List<BedragenDaoPojo> lstBedragen = new ArrayList<BedragenDaoPojo>();
        lstBedragen.add(new BedragenDaoPojo(bedrag1));
        lstBedragen.add(new BedragenDaoPojo(bedrag2));
        lstBedragen.add(new BedragenDaoPojo(bedrag3));
        
        when(bedragenDao.BedragInPeriode(dt.parse("2015-02-18"), dt.parse("2015-02-23"), null, 1, userInfo.getPersoon().getPk_id())).thenReturn(lstBedragen);
        when(groepenDao.findOne(groepNegatief.getPk_id())).thenReturn(groepNegatief);
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTot = 
                bedragenSrv.rapportOverzichtGroepBedragen(groepNegatief.getPk_id(), 0, dt.parse("2015-02-18"), dt.parse("2015-02-23"), 1, new OverzichtGroepBedragenTotal());
        
        assertEquals(new BigDecimal(250), overzichtGroepBedragenTot.getSomBedrag());
    }
    
    @Test
    public void testRapportOverzichtGroepBedragenWithoutTypeKostOpbrengsten() throws ParseException{
        when(bedrag1.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag2.getBedrag()).thenReturn(new BigDecimal(100));
        when(bedrag3.getBedrag()).thenReturn(new BigDecimal(100));
        
        when(mainGroup.getPk_id()).thenReturn(1);
        when(groepPositief.getPk_id()).thenReturn(2);
        when(groepNegatief.getPk_id()).thenReturn(3);
        when(groepPositief.getHoofdGroep()).thenReturn(mainGroup);
        when(groepNegatief.getHoofdGroep()).thenReturn(mainGroup);
        when(groepPositief.getNegatief()).thenReturn(0);
        when(groepNegatief.getNegatief()).thenReturn(1);
        
        when(bedrag1.getGroep()).thenReturn(groepPositief);
        when(bedrag2.getGroep()).thenReturn(groepNegatief);
        when(bedrag3.getGroep()).thenReturn(groepPositief);
                
        List<BedragenDaoPojo> lstBedragen = new ArrayList<BedragenDaoPojo>();
        lstBedragen.add(bedrag1);
        lstBedragen.add(bedrag2);
        lstBedragen.add(bedrag3);
        
        when(bedragenDao.BedragInPeriode(dt.parse("2015-02-18"), dt.parse("2015-02-23"), null, 1, userInfo.getPersoon().getPk_id())).thenReturn(lstBedragen);
        
        when(groepenDao.findOne(mainGroup.getPk_id())).thenReturn(mainGroup);
        when(groepenDao.findOne(groepPositief.getPk_id())).thenReturn(groepPositief);
        
        OverzichtGroepBedragenTotal overzichtGroepBedragenTot = 
                bedragenSrv.rapportOverzichtGroepBedragen(mainGroup.getPk_id(), dt.parse("2015-02-18"), dt.parse("2015-02-23"), 1, new OverzichtGroepBedragenTotal());
        
        assertEquals(new BigDecimal(100), overzichtGroepBedragenTot.getSomBedrag());
    }
}

