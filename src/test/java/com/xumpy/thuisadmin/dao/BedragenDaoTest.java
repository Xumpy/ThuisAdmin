/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao;

import com.xumpy.security.model.UserInfo;
import com.xumpy.security.root.InitDatabase;
import com.xumpy.security.root.InitOldDatabase;
import com.xumpy.security.root.InitServices;
import com.xumpy.security.root.UserService;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragenTotal;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.GroepenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.PersonenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.GroepenDaoPojo;
import com.xumpy.thuisadmin.dao.model.PersonenDaoPojo;
import com.xumpy.thuisadmin.dao.model.RekeningenDaoPojo;
import com.xumpy.thuisadmin.domain.Bedragen;
import com.xumpy.thuisadmin.domain.Groepen;
import com.xumpy.thuisadmin.domain.Personen;
import com.xumpy.thuisadmin.domain.Rekeningen;
import com.xumpy.thuisadmin.services.BedragenSrv;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.xumpy.timesheets.services.session.SessionTimesheet;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author nicom
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitDatabase.class, InitOldDatabase.class, InitServices.class, UserService.class})
@ActiveProfiles("junit")
@WebAppConfiguration
public class BedragenDaoTest{
    @Autowired PersonenDaoImpl personenDao;
    @Autowired GroepenDaoImpl groepenDao;
    @Autowired RekeningenDaoImpl rekeningenDao;
    @Autowired BedragenDaoImpl bedragenDao;
    @Autowired UserInfo userInfo;
    @Autowired BedragenSrv bedragenSrv;

    private Personen personen;
    private Groepen groep;
    private Rekeningen rekening;
    
    Bedragen bedrag;
    
    private static final Integer BEDRAG_PK_ID = 30;
    
    private static final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd"); 
    private Date startDate;
    private Date endDate;

    @Before
    @Transactional(value="jpaTransactionManager")
    public void setUpBedragen(){
        personen = personenDao.findOne(1);
        groep = groepenDao.findOne(1);
        rekening = rekeningenDao.findOne(1);
        
        bedrag = Mockito.mock(Bedragen.class);
        
        when(bedrag.getPk_id()).thenReturn(BEDRAG_PK_ID);
        when(bedrag.getBedrag()).thenReturn(new BigDecimal(200));
        when(bedrag.getPersoon()).thenReturn(personen);
        when(bedrag.getGroep()).thenReturn(groep);
        when(bedrag.getDatum()).thenReturn(new Date(new java.util.Date().getTime()));
        when(bedrag.getOmschrijving()).thenReturn("test bedrag");
        when(bedrag.getRekening()).thenReturn(rekening);
    }
    
    private List<Bedragen> fetchTestBedragen() throws ParseException{
        List<Bedragen> lstBedragen = new ArrayList<Bedragen>();
        GroepenDaoPojo groepNegatief = new GroepenDaoPojo(groepenDao.findOne(2));
        GroepenDaoPojo groepPositief = new GroepenDaoPojo(groepenDao.findOne(3));
        
        for (int i=0; i<8; i++){
            BedragenDaoPojo bedrag = new BedragenDaoPojo();
            switch(i){
                case 0: bedrag.setPk_id(1);bedrag.setBedrag(new BigDecimal(200));bedrag.setGroep(groepNegatief);bedrag.setDatum(new Date(dt.parse("2015-02-18").getTime()));
                        break;
                case 1: bedrag.setPk_id(3);bedrag.setBedrag(new BigDecimal(50));bedrag.setGroep(groepNegatief);bedrag.setDatum(new Date(dt.parse("2015-02-19").getTime()));
                        break;
                case 2: bedrag.setPk_id(2);bedrag.setBedrag(new BigDecimal(100));bedrag.setGroep(groepNegatief);bedrag.setDatum(new Date(dt.parse("2015-02-19").getTime()));
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
            
            bedrag.setPersoon(new PersonenDaoPojo(personen));
            bedrag.setRekening(new RekeningenDaoPojo(rekening));
            bedrag.setOmschrijving("test");
            
            lstBedragen.add(bedrag);
        }
        
        return lstBedragen;
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testSave(){
        bedragenDao.save(new BedragenDaoPojo(bedrag));
        Bedragen bedragTest = bedragenDao.findOne(BEDRAG_PK_ID);
        assertEquals(bedrag.getPk_id(), bedragTest.getPk_id());
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testUpdate(){
        bedragenDao.save(new BedragenDaoPojo(bedrag));
        
        BedragenDaoPojo bedragForUpdate = new BedragenDaoPojo(bedragenDao.findOne(BEDRAG_PK_ID));
        bedragForUpdate.setBedrag(new BigDecimal(2000));
        bedragenDao.save(bedragForUpdate);
        
        Bedragen bedragTest = bedragenDao.findOne(BEDRAG_PK_ID);
        assertEquals(new BigDecimal(2000), bedragTest.getBedrag());
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testDelete(){
        bedragenDao.save(new BedragenDaoPojo(bedrag));
        Bedragen bedragForDelete = bedragenDao.findOne(BEDRAG_PK_ID);

        bedragenDao.save(new BedragenDaoPojo(bedragForDelete));
        
        bedragenDao.delete(new BedragenDaoPojo(bedragForDelete));
        
        Bedragen bedragNull = bedragenDao.findOne(BEDRAG_PK_ID);
        assertNull(bedragNull);
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testReportBedragen() throws ParseException{
        List<Bedragen> lstBedragenExpected = new ArrayList<Bedragen>();
        
        bedrag = bedragenDao.findOne(24);
        lstBedragenExpected.add(bedrag);
        
        userInfo.setPersoon(personenDao.findOne(2));
        rekening = rekeningenDao.findOne(3);
        
        Pageable topTen = new PageRequest(0, 10); 
        List<? extends Bedragen> lstBedragenResult = bedragenDao.reportBedragen(rekening.getPk_id(), "nog een test", userInfo.getPersoon().getPk_id(), topTen);
        
        assertEquals(lstBedragenExpected, lstBedragenResult);
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testGetNewPkId(){
        Integer newPkId = bedragenDao.getNewPkId();
        assertEquals(newPkId, new Integer(27));
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testSomBedragDatum() throws ParseException{
        startDate = new Date(dt.parse("2015-02-18").getTime());
        endDate = new Date(dt.parse("2015-02-23").getTime());
        
        BigDecimal somBedrag = bedragenDao.somBedragDatum(rekening.getPk_id(), startDate);
        
        assertEquals(new BigDecimal(1550), somBedrag);
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testBedragenInPeriode() throws ParseException{
        userInfo.setPersoon(personenDao.findOne(1));
        startDate = new Date(dt.parse("2015-02-18").getTime());
        endDate = new Date(dt.parse("2015-02-23").getTime());
        
        List<Bedragen> checkBedragen = fetchTestBedragen();
        
        List<? extends Bedragen> bedragInPeriode = bedragenDao.BedragInPeriode(startDate, endDate, rekening.getPk_id(), 0, userInfo.getPersoon().getPk_id());

        for(int i=0; i<checkBedragen.size();i++){
            assertEquals(checkBedragen.get(i).getPk_id(), bedragInPeriode.get(i).getPk_id());
        }
    }
    
    //Integration test must be called in dao (for real database) but accesses service layer :(
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testGetBedragAtDate() throws ParseException{
        userInfo.setPersoon(personenDao.findOne(1));
        startDate = new Date(dt.parse("2015-02-18").getTime());
        endDate = new Date(dt.parse("2015-02-23").getTime());
        
        BigDecimal bedragAtStartDate = bedragenSrv.getBedragAtDate(startDate, rekening);
        BigDecimal bedragAtEndDate = bedragenSrv.getBedragAtDate(endDate, rekening);
        
        assertEquals(new BigDecimal(450), bedragAtStartDate);
        assertEquals(new BigDecimal(2080), bedragAtEndDate);
    }
    
    @Test
    @Transactional(value="jpaTransactionManager")
    public void testReportBedragenNull() throws ParseException{
        userInfo.setPersoon(personenDao.findOne(2));
        rekening = rekeningenDao.findOne(3);
        
        Pageable topTen = new PageRequest(0, 10); 
        List<? extends Bedragen> lstBedragenResult = bedragenDao.reportBedragen(rekening.getPk_id(), null, userInfo.getPersoon().getPk_id(), topTen);
        
        assertEquals(true, (lstBedragenResult.size() > 0)); // No Error occured
    }
}
