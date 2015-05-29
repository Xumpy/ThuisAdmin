/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model.OverviewWorkSrvPojo;

import com.xumpy.timesheets.domain.OverviewWork.MonthlyOverviewWorkDetails;
import com.xumpy.timesheets.domain.OverviewWork.OverviewWork;
import com.xumpy.timesheets.domain.OverviewWork.OverviewWorkDetails;
import java.util.List;

/**
 *
 * @author nicom
 */
public class OverviewWorkSrvPojo implements OverviewWork{
    private OverviewWorkDetails workDetails;
    private List<MonthlyOverviewWorkDetailsSrvPojo> monthlyWorkDetails;

    public void setWorkDetails(OverviewWorkDetails workDetails) {
        this.workDetails = workDetails;
    }

    public void setMonthlyWorkDetails(List<MonthlyOverviewWorkDetailsSrvPojo> monthlyWorkDetails) {
        this.monthlyWorkDetails = monthlyWorkDetails;
    }
    
    @Override
    public OverviewWorkDetails getWorkDetails() {
        return this.workDetails;
    }

    @Override
    public List<? extends MonthlyOverviewWorkDetails> getMonthlyWorkDetails() {
        return this.monthlyWorkDetails;
    }
    
    public OverviewWorkSrvPojo(){}
    
    public OverviewWorkSrvPojo(OverviewWork overviewWork){
        for (MonthlyOverviewWorkDetails monthlyOverviewWorkDetails: overviewWork.getMonthlyWorkDetails()){
            this.monthlyWorkDetails.add(new MonthlyOverviewWorkDetailsSrvPojo(monthlyOverviewWorkDetails));
        }
        this.workDetails = new OverviewWorkDetailsSrvPojo(overviewWork.getWorkDetails());
    }
}
