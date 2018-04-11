/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.integration;

import com.xumpy.Application;
import com.xumpy.security.model.UserInfo;
import com.xumpy.security.root.UserService;
import com.xumpy.thuisadmin.controllers.model.RekeningOverzicht;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.PersonenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.RekeningenDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.services.BedragenSrv;
import com.xumpy.thuisadmin.services.implementations.BedragenSrvImpl;
import com.xumpy.utilities.LocalTestProfile;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static junit.framework.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = Application.class)
@WebAppConfiguration()
@ActiveProfiles("junit-database")
@Sql(scripts="/data.sql")
@Ignore /* Only works with integration test with the database, this has to be refactored but is not important for now */
public class GraphiekTests {
    
    @Autowired BedragenSrv bedragenSrv;
    @Autowired UserInfo userInfo;
    @Autowired PersonenDaoImpl personenDao;
    @Autowired BedragenDaoImpl bedragenDao;
    @Autowired RekeningenDaoImpl rekeningenDao;
    
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    
    @Test
    public void testGraphiek() throws ParseException{
        userInfo.setPersoon(personenDao.findById(1).get());
        
        Date startDate = format.parse("05/04/2014");
        Date eindDate = format.parse("13/04/2014");

        List<RekeningOverzicht> lstrekeningOverzicht = bedragenSrv.graphiekBedrag(null, startDate, eindDate);
        
        Map<Date, BigDecimal> rekeningOverzichtMap = new HashMap<Date, BigDecimal>();
        for(RekeningOverzicht rekeningOverzicht: lstrekeningOverzicht){
            rekeningOverzichtMap.put(rekeningOverzicht.getDatum(), rekeningOverzicht.getBedrag());
        }
        
        assertEquals(new BigDecimal("2890.48"), rekeningOverzichtMap.get(format.parse("05/04/2014")));
        assertEquals(new BigDecimal("2840.48"), rekeningOverzichtMap.get(format.parse("07/04/2014")));
        assertEquals(new BigDecimal("2825.72"), rekeningOverzichtMap.get(format.parse("08/04/2014")));
        assertEquals(new BigDecimal("2732.28"), rekeningOverzichtMap.get(format.parse("09/04/2014")));
        assertEquals(new BigDecimal("2635.15"), rekeningOverzichtMap.get(format.parse("11/04/2014")));
        assertEquals(new BigDecimal("2548.84"), rekeningOverzichtMap.get(format.parse("12/04/2014")));
    }
    
    @Test
    public void testDaoGraphiek() throws ParseException{
        List<BedragenDaoPojo> lstBedragenPojo = bedragenDao.BedragInPeriode(format.parse("05/04/2014"), format.parse("13/04/2014"), null, 0, 1);
        
        assertEquals(17, lstBedragenPojo.size());
        assertEquals(new BigDecimal("5.75"), lstBedragenPojo.get(0).getBedrag());
        assertEquals(new BigDecimal("50.00"), lstBedragenPojo.get(1).getBedrag());
        assertEquals(new BigDecimal("117.00"), lstBedragenPojo.get(2).getBedrag());
        assertEquals(new BigDecimal("50.00"), lstBedragenPojo.get(3).getBedrag());
        assertEquals(new BigDecimal("7.34"), lstBedragenPojo.get(4).getBedrag());
        assertEquals(new BigDecimal("7.42"), lstBedragenPojo.get(5).getBedrag());
        assertEquals(new BigDecimal("10.00"), lstBedragenPojo.get(6).getBedrag());
        assertEquals(new BigDecimal("10.00"), lstBedragenPojo.get(7).getBedrag());
        assertEquals(new BigDecimal("73.44"), lstBedragenPojo.get(8).getBedrag());
        assertEquals(new BigDecimal("1.98"), lstBedragenPojo.get(9).getBedrag());
        assertEquals(new BigDecimal("45.15"), lstBedragenPojo.get(10).getBedrag());
        assertEquals(new BigDecimal("50.00"), lstBedragenPojo.get(11).getBedrag());
        assertEquals(new BigDecimal("8.70"), lstBedragenPojo.get(12).getBedrag());
        assertEquals(new BigDecimal("14.60"), lstBedragenPojo.get(13).getBedrag());
        assertEquals(new BigDecimal("17.00"), lstBedragenPojo.get(14).getBedrag());
        assertEquals(new BigDecimal("46.01"), lstBedragenPojo.get(15).getBedrag());
        assertEquals(new BigDecimal("44.94"), lstBedragenPojo.get(16).getBedrag());
    }
    
    @Test
    public void testBedragAtDate() throws ParseException{
        BigDecimal rekeningStand = bedragenSrv.getBedragAtDate(format.parse("05/04/2014"), null);
        
        assertEquals(new BigDecimal("2890.48"), rekeningStand);
    }
}
