/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.model.db.Bedragen;
import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
@EnableTransactionManagement
public class BedragenDaoTest extends H2InMemory{
    
    private Personen persoon;
    private Groepen groep;
    private Rekeningen rekening;
    private Bedragen bedrag;
    
    private static final Integer BEDRAG_PK_ID = 13;
    
    private static final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
    private Date startDate;
    private Date endDate;

    public BedragenDaoTest() throws ParseException {
        this.startDate = new Date(dt.parse("2015-02-18").getTime());
        this.endDate = new Date(dt.parse("2015-02-23").getTime());
    }

    @Before
    public void setUpBedragen(){
        persoon = personenDao.findPersoon(1);
        groep = groepenDao.findGroep(1);
        rekening = rekeningenDao.findRekening(1);
        
        bedrag = new Bedragen();
        bedrag.setPk_id(BEDRAG_PK_ID);
        bedrag.setBedrag(new BigDecimal(200));
        bedrag.setPersoon(persoon);
        bedrag.setGroep(groep); 
        bedrag.setDatum(new Date(new java.util.Date().getTime()));
        bedrag.setOmschrijving("test bedrag");
        bedrag.setRekening(rekening);
    }
    
    private List<Bedragen> fetchTestBedragen() throws ParseException{
        List<Bedragen> lstBedragen = new ArrayList<Bedragen>();
        Groepen groepNegatief = groepenDao.findGroep(2);
        Groepen groepPositief = groepenDao.findGroep(3);
        
        for (int i=0; i<8; i++){
            Bedragen bedrag = new Bedragen();
            switch(i){
                case 0: bedrag.setPk_id(1);bedrag.setBedrag(new BigDecimal(200));bedrag.setGroep(groepNegatief);bedrag.setDatum(new Date(dt.parse("2015-02-18").getTime()));
                        break;
                case 1: bedrag.setPk_id(2);bedrag.setBedrag(new BigDecimal(100));bedrag.setGroep(groepNegatief);bedrag.setDatum(new Date(dt.parse("2015-02-19").getTime()));
                        break;
                case 2: bedrag.setPk_id(3);bedrag.setBedrag(new BigDecimal(50));bedrag.setGroep(groepNegatief);bedrag.setDatum(new Date(dt.parse("2015-02-19").getTime()));
                        break;
                case 3: bedrag.setPk_id(4);bedrag.setBedrag(new BigDecimal(2000));bedrag.setGroep(groepPositief);bedrag.setDatum(new Date(dt.parse("2015-02-20").getTime()));
                        break;
                case 4: bedrag.setPk_id(5);bedrag.setBedrag(new BigDecimal(50));bedrag.setGroep(groepNegatief);bedrag.setDatum(new Date(dt.parse("2015-02-21").getTime()));
                        break;
                case 5: bedrag.setPk_id(6);bedrag.setBedrag(new BigDecimal(60));bedrag.setGroep(groepNegatief);bedrag.setDatum(new Date(dt.parse("2015-02-21").getTime()));
                        break;
                case 6: bedrag.setPk_id(7);bedrag.setBedrag(new BigDecimal(70));bedrag.setGroep(groepNegatief);bedrag.setDatum(new Date(dt.parse("2015-02-22").getTime()));
                        break;
                case 7: bedrag.setPk_id(8);bedrag.setBedrag(new BigDecimal(40));bedrag.setGroep(groepNegatief);bedrag.setDatum(new Date(dt.parse("2015-02-23").getTime()));
                        break;
            }
            
            bedrag.setPersoon(persoon);
            bedrag.setRekening(rekening);
            bedrag.setOmschrijving("test");
            
            lstBedragen.add(bedrag);
        }
        
        return lstBedragen;
    }
    
    @Test
    public void testSave(){
        bedragenDao.save(bedrag);
        Bedragen bedragTest = bedragenDao.getBedrag(BEDRAG_PK_ID);
        assertEquals(bedrag, bedragTest);
    }
    
    @Test
    public void testUpdate(){
        bedragenDao.save(bedrag);
        
        Bedragen bedragForUpdate = bedragenDao.getBedrag(BEDRAG_PK_ID);
        bedragForUpdate.setBedrag(new BigDecimal(2000));
        bedragenDao.update(bedragForUpdate);
        
        Bedragen bedragTest = bedragenDao.getBedrag(BEDRAG_PK_ID);
        assertEquals(new BigDecimal(2000), bedragTest.getBedrag());
    }
    
    @Test
    public void testDelete(){
        bedragenDao.save(bedrag);
        
        Bedragen bedragForDelete = bedragenDao.getBedrag(BEDRAG_PK_ID);
        bedragenDao.delete(bedragForDelete);
        
        Bedragen bedragNull = bedragenDao.getBedrag(BEDRAG_PK_ID);
        assertNull(bedragNull);
    }
    
    @Test
    public void testBedragenInPeriode() throws ParseException{
        List<Bedragen> checkBedragen = fetchTestBedragen();
        
        List<Bedragen> bedragInPeriode = bedragenDao.BedragInPeriode(startDate, endDate);

        assertEquals(checkBedragen, bedragInPeriode);
    }
    
    @Test
    public void testGetBedragAtDate(){
        BigDecimal bedragAtStartDate = bedragenDao.getBedragAtDate(startDate, rekening.getWaarde());
        BigDecimal bedragAtEndDate = bedragenDao.getBedragAtDate(endDate, rekening.getWaarde());
        
        assertEquals(bedragAtStartDate, new BigDecimal(450));
        assertEquals(bedragAtEndDate, new BigDecimal(2080));

    }
    
    @Test
    public void testOverviewRekeningData(){
        Map overviewRekeningTestData = new HashMap();
        overviewRekeningTestData.put("2015-02-18", 450);
        overviewRekeningTestData.put("2015-02-19", 300);
        overviewRekeningTestData.put("2015-02-20", 2300);
        overviewRekeningTestData.put("2015-02-21", 2190);
        overviewRekeningTestData.put("2015-02-22", 2120);
        overviewRekeningTestData.put("2015-02-23", 2080);
        
        Map overviewRekeningData = bedragenDao.OverviewRekeningData(bedragenDao.BedragInPeriode(startDate, endDate), rekening.getWaarde());
        
        assertEquals(overviewRekeningData, overviewRekeningTestData);
    }
}
