/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.logic;

import com.xumpy.thuisadmin.model.view.BeheerBedragenInp;
import com.xumpy.thuisadmin.model.view.BeheerBedragenReportLst;

/**
 *
 * @author nicom
 */
public class BeheerBedragenButtons {
    public static BeheerBedragenReportLst setButtons(BeheerBedragenReportLst beheerBedragenReportLst, BeheerBedragenInp beheerBedragenInp){
        if (beheerBedragenInp.getOffset().equals(0)){
            beheerBedragenReportLst.setShowPrevious(false);
        } else {
            beheerBedragenReportLst.setShowPrevious(true);
        }
        
        if (beheerBedragenReportLst.getBeheerBedragenReport() != null && beheerBedragenReportLst.getBeheerBedragenReport().size() == 10){
            beheerBedragenReportLst.setShowNext(true);
        } else {
            beheerBedragenReportLst.setShowNext(false);
        }
        
        return beheerBedragenReportLst;
    }
}
