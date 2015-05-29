/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.timesheets.services.model.OverviewWorkSrvPojo;

import com.xumpy.timesheets.domain.OverviewWork.MonthlyOverviewWorkDetails;
import com.xumpy.timesheets.domain.OverviewWork.OverviewWorkDetails;

public class MonthlyOverviewWorkDetailsSrvPojo implements MonthlyOverviewWorkDetails{
    private String month;
    private OverviewWorkDetailsSrvPojo workDetails;

    public void setMonth(String month) {
        this.month = month;
    }

    public void setWorkDetails(OverviewWorkDetailsSrvPojo workDetails) {
        this.workDetails = workDetails;
    }

    @Override
    public String getMonth() {
        return this.month;
    }

    @Override
    public OverviewWorkDetails getWorkDetails() {
        return this.workDetails;
    }

    public MonthlyOverviewWorkDetailsSrvPojo(){}

    public MonthlyOverviewWorkDetailsSrvPojo(MonthlyOverviewWorkDetails monthlyOverviewWorkDetails){
        this.month = monthlyOverviewWorkDetails.getMonth();
        this.workDetails = new OverviewWorkDetailsSrvPojo(monthlyOverviewWorkDetails.getWorkDetails());
    }

}