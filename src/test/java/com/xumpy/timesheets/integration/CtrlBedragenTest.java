/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.integration;

import com.xumpy.security.model.UserInfo;
import com.xumpy.security.root.InitDatabase;
import com.xumpy.security.root.InitOldDatabase;
import com.xumpy.security.root.UserService;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenInp;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenReportLst;
import com.xumpy.thuisadmin.dao.implementations.BedragenDaoImpl;
import com.xumpy.thuisadmin.dao.implementations.PersonenDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.services.BedragenSrv;
import com.xumpy.thuisadmin.services.implementations.BedragenSrvImpl;
import com.xumpy.utilities.LocalTestProfile;
import java.util.List;
import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author nico
 */
@Category(LocalTestProfile.class) // Test will only work with specific production database which can be migrated
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {InitDatabase.class, InitOldDatabase.class, UserService.class, BedragenSrvImpl.class})
@ActiveProfiles("tst_local")
public class CtrlBedragenTest {
    
    @Autowired
    private UserInfo userInfo;
    
    @Autowired
    private PersonenDaoImpl persoonDao;
    
    @Autowired
    private BedragenDaoImpl bedragenDao;
    
    @Autowired
    private BedragenSrv bedragenSrv;
    
    private static Logger log = Logger.getLogger(CtrlBedragenTest.class);
    
    @Test
    public void testFetchBedragen(){
        userInfo.setPersoon(persoonDao.findOne(1));
        BeheerBedragenReportLst beheerBedragenReportLst = new BeheerBedragenReportLst();
        
        BeheerBedragenInp beheerBedragenInp = new BeheerBedragenInp();
        beheerBedragenInp.setOffset(0);
        beheerBedragenInp.setRekening(null);
        beheerBedragenInp.setZoekOpdracht(null);
        
        beheerBedragenReportLst = bedragenSrv.reportBedragen(beheerBedragenReportLst, beheerBedragenInp.getOffset(), beheerBedragenInp.getRekening(), beheerBedragenInp.getZoekOpdracht());
        beheerBedragenReportLst = BedragenSrvImpl.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(10, beheerBedragenReportLst.getBeheerBedragenReport().size());
        
        beheerBedragenInp.setZoekOpdracht("Mobistar");
        
        beheerBedragenReportLst = bedragenSrv.reportBedragen(beheerBedragenReportLst, beheerBedragenInp.getOffset(), beheerBedragenInp.getRekening(), beheerBedragenInp.getZoekOpdracht());
        beheerBedragenReportLst = BedragenSrvImpl.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(10, beheerBedragenReportLst.getBeheerBedragenReport().size());
        
        beheerBedragenInp.setZoekOpdracht("mobistar");
        
        beheerBedragenReportLst = bedragenSrv.reportBedragen(beheerBedragenReportLst, beheerBedragenInp.getOffset(), beheerBedragenInp.getRekening(), beheerBedragenInp.getZoekOpdracht());
        beheerBedragenReportLst = BedragenSrvImpl.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(10, beheerBedragenReportLst.getBeheerBedragenReport().size());
        
        beheerBedragenInp.setZoekOpdracht("bist");
        
        beheerBedragenReportLst = bedragenSrv.reportBedragen(beheerBedragenReportLst, beheerBedragenInp.getOffset(), beheerBedragenInp.getRekening(), beheerBedragenInp.getZoekOpdracht());
        beheerBedragenReportLst = BedragenSrvImpl.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(10, beheerBedragenReportLst.getBeheerBedragenReport().size());
    }
}
