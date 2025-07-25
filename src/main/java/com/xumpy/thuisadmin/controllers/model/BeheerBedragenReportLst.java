/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicom
 */
@Component
@Scope("session")
public class BeheerBedragenReportLst implements Serializable{
    private boolean showPrevious;
    private boolean showNext;
    private List<BeheerBedragenReport> beheerBedragenReport;

    private static Logger log = LoggerFactory.getLogger(BeheerBedragenReportLst.class);
    
    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public List<BeheerBedragenReport> getBeheerBedragenReport() {
        return beheerBedragenReport;
    }

    public void setBeheerBedragenReport(List<BeheerBedragenReport> beheerBedragenReport) {
        this.beheerBedragenReport = beheerBedragenReport;
    }
    
    public BeheerBedragenReportLst(){
        this.showNext = true;
        this.showPrevious = false;
    }
    
    public void debug(){
        log.info("showPrevious: " + showPrevious);
        log.info("showNext: " + showNext);
        for(BeheerBedragenReport report: beheerBedragenReport){
            log.info("--------------------");
            report.debug();
            log.info("--------------------");
        }
    }
}
