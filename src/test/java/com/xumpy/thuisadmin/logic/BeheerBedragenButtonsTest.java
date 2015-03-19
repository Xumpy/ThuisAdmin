/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.logic;

import com.xumpy.thuisadmin.model.view.BeheerBedragenInp;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReport;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReportLst;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author nicom
 */
@RunWith(MockitoJUnitRunner.class)
public class BeheerBedragenButtonsTest {
    @Spy BeheerBedragenReportLst beheerBedragenReportLst;
    @Spy BeheerBedragenInp beheerBedragenInp;
    @Mock BeheerBedragenReport beheerBedragenReport;
    
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
        
        BeheerBedragenReportLst result = BeheerBedragenButtons.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(result.isShowNext(), false);
        assertEquals(result.isShowPrevious(), false);
    }
    
    @Test
    public void testSetButtonsTestNoPreviousYesNext(){
        when(beheerBedragenInp.getOffset()).thenReturn(0);
        mockSizeBeheerBedragenReportLst(10);
        
        BeheerBedragenReportLst result = BeheerBedragenButtons.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(result.isShowNext(), true);
        assertEquals(result.isShowPrevious(), false);
    }
    
    @Test
    public void testSetButtonsTestYesPreviousNoNext(){
        when(beheerBedragenInp.getOffset()).thenReturn(1);
        mockSizeBeheerBedragenReportLst(5);
        
        BeheerBedragenReportLst result = BeheerBedragenButtons.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(result.isShowNext(), false);
        assertEquals(result.isShowPrevious(), true);
    }
    
    @Test
    public void testSetButtonsTestYesPreviousYesNext(){
        when(beheerBedragenInp.getOffset()).thenReturn(1);
        mockSizeBeheerBedragenReportLst(10);
        
        BeheerBedragenReportLst result = BeheerBedragenButtons.setButtons(beheerBedragenReportLst, beheerBedragenInp);
        
        assertEquals(result.isShowNext(), true);
        assertEquals(result.isShowPrevious(), true);
    }
}
