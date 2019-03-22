package com.xumpy.timesheets.controller.model;

import com.xumpy.government.controllers.model.VatCompensationCtrlPojo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
@Scope("session")
public class VatCompOverviewRequestCtrlPojo {
    String beginDate;
    String endDate;
    VatCompensationCtrlPojo vatCompensation;

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

    public VatCompensationCtrlPojo getVatCompensation() {
        return vatCompensation;
    }

    public void setVatCompensation(VatCompensationCtrlPojo vatCompensation) {
        this.vatCompensation = vatCompensation;
    }

    public VatCompOverviewRequestCtrlPojo(){
        SimpleDateFormat dt = new SimpleDateFormat("MM/yyyy");

        Date dateToday = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToday);
        cal.add(Calendar.YEAR, -1);

        Date prevMonth = cal.getTime();

        this.beginDate = dt.format(prevMonth);
        this.endDate = dt.format(dateToday);
    }
}