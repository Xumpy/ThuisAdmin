/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model.finances.overviewMonthCategory;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author nicom
 */
@Component
@Scope("session")
public class OverviewMonthCategoryInput implements Serializable {
    String beginDate;
    String endDate;
    List<MainGroupValue> mainGroupValues;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<MainGroupValue> getMainGroupValues() {
        return mainGroupValues;
    }

    public void setMainGroupValues(List<MainGroupValue> mainGroupValues) {
        this.mainGroupValues = mainGroupValues;
    }
    
    public OverviewMonthCategoryInput(){
        SimpleDateFormat dt = new SimpleDateFormat("MM/yyyy"); 
        
        Date dateToday = new Date();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToday);
        cal.add(Calendar.YEAR, -1);
        
        Date prevMonth = cal.getTime();
        
        this.beginDate = dt.format(prevMonth);
        this.endDate = dt.format(dateToday);
        this.mainGroupValues = new ArrayList<MainGroupValue>();
    }
}
