/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.logic;

import com.xumpy.thuisadmin.controllers.model.BeheerBedragenInp;
import com.xumpy.thuisadmin.controllers.model.BeheerBedragenReportLst;

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
