/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.thuisadmin.dao.setup.H2InMemory;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.model.Bedragen;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
@EnableTransactionManagement
public class BedragenDaoTest extends H2InMemory{
    
    private PersonenDaoPojo personen;
    private GroepenDaoPojo groep;
    private RekeningenDaoPojo rekening;
    private BedragenDaoPojo bedrag;
    
    private static final Integer BEDRAG_PK_ID = 30;
    
    private static final Integer GROEP_NEGATIEF_ID = 2;
    private static final Integer GROEP_POSITIEF_ID = 3;
    
    private static final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
    private Date startDate;
    private Date endDate;

    @Before
    public void setUpBedragen(){
        personen = personenDao.findPersoon(1);
        groep = groepenDao.findGroep(1);
        rekening = rekeningenDao.findRekening(1);
        
        bedrag = new BedragenDaoPojo();
        bedrag.setPk_id(BEDRAG_PK_ID);
        bedrag.setBedrag(new BigDecimal(200));
        bedrag.setPersoon(personen);
        bedrag.setGroep(groep); 
        bedrag.setDatum(new Date(new java.util.Date().getTime()));
        bedrag.setOmschrijving("test bedrag");
        bedrag.setRekening(rekening);
    }
    
    private List<Bedragen> fetchTestBedragen() throws ParseException{
        List<Bedragen> lstBedragen = new ArrayList<Bedragen>();
        GroepenDaoPojo groepNegatief = groepenDao.findGroep(2);
        GroepenDaoPojo groepPositief = groepenDao.findGroep(3);
        
        for (int i=0; i<8; i++){
            BedragenDaoPojo bedrag = new BedragenDaoPojo();
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
            
            bedrag.setPersoon(personen);
            bedrag.setRekening(rekening);
            bedrag.setOmschrijving("test");
            
            lstBedragen.add(bedrag);
        }
        
        return lstBedragen;
    }
    
    @Test
    public void testSave(){
        bedragenDao.save(bedrag);
        BedragenDaoPojo bedragTest = bedragenDao.findBedrag(BEDRAG_PK_ID);
        assertEquals(bedrag, bedragTest);
    }
    
    @Test
    public void testUpdate(){
        bedragenDao.save(bedrag);
        
        BedragenDaoPojo bedragForUpdate = bedragenDao.findBedrag(BEDRAG_PK_ID);
        bedragForUpdate.setBedrag(new BigDecimal(2000));
        bedragenDao.update(bedragForUpdate);
        
        BedragenDaoPojo bedragTest = bedragenDao.findBedrag(BEDRAG_PK_ID);
        assertEquals(new BigDecimal(2000), bedragTest.getBedrag());
    }
    
    @Test
    public void testDelete(){
        bedragenDao.save(bedrag);
        
        BedragenDaoPojo bedragForDelete = bedragenDao.findBedrag(BEDRAG_PK_ID);
        bedragenDao.delete(bedragForDelete);
        
        BedragenDaoPojo bedragNull = bedragenDao.findBedrag(BEDRAG_PK_ID);
        assertNull(bedragNull);
    }
    
    @Test
    public void testReportBedragen() throws ParseException{
        List<BedragenDaoPojo> lstBedragenExpected = new ArrayList<BedragenDaoPojo>();
        
        bedrag = bedragenDao.findBedrag(24);
        lstBedragenExpected.add(bedrag);
        
        when(persoon.getPk_id()).thenReturn(2);
        rekening = rekeningenDao.findRekening(3);
        List<Bedragen> lstBedragenResult = bedragenDao.reportBedragen(rekening, 0, "nog een test");
        
        assertEquals(lstBedragenExpected, lstBedragenResult);
    }
    
    @Test
    public void testGetNewPkId(){
        Integer newPkId = bedragenDao.getNewPkId();
        assertEquals(newPkId, new Integer(27));
    }
    
    @Test
    public void testSomBedragDatum() throws ParseException{
        startDate = new Date(dt.parse("2015-02-18").getTime());
        endDate = new Date(dt.parse("2015-02-23").getTime());
        
        BigDecimal somBedrag = bedragenDao.somBedragDatum(rekening, startDate);
        
        assertEquals(new BigDecimal(1550), somBedrag);
    }
    
    @Test
    public void testBedragenInPeriode() throws ParseException{
        startDate = new Date(dt.parse("2015-02-18").getTime());
        endDate = new Date(dt.parse("2015-02-23").getTime());
        
        List<Bedragen> checkBedragen = fetchTestBedragen();
        
        List<Bedragen> bedragInPeriode = bedragenDao.BedragInPeriode(startDate, endDate, rekening, false);

        for(int i=0; i<checkBedragen.size();i++){
            assertEquals(checkBedragen.get(i).getPk_id(), bedragInPeriode.get(i).getPk_id());
        }
    }
    
    @Test
    public void testGetBedragAtDate() throws ParseException{
        startDate = new Date(dt.parse("2015-02-18").getTime());
        endDate = new Date(dt.parse("2015-02-23").getTime());
        
        BigDecimal bedragAtStartDate = bedragenDao.getBedragAtDate(startDate, rekening);
        BigDecimal bedragAtEndDate = bedragenDao.getBedragAtDate(endDate, rekening);
        
        assertEquals(new BigDecimal(450), bedragAtStartDate);
        assertEquals(new BigDecimal(2080), bedragAtEndDate);
    }
    
    @Test
    public void testReportBedragenNull() throws ParseException{
        when(persoon.getPk_id()).thenReturn(2);
        rekening = rekeningenDao.findRekening(3);
        List<Bedragen> lstBedragenResult = bedragenDao.reportBedragen(rekening, 0, null);
        
        assertEquals(true, (lstBedragenResult.size() > 0)); // No Error occured
    }
}
