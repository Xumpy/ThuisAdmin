/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.model.Rekeningen;
import com.xumpy.thuisadmin.services.model.RekeningenSrvPojo;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nico
 */
@Component
@Scope("session")
public class FinanceHeader implements Serializable{
    private String beginDatum;
    private String eindDatum;
    private RekeningenSrvPojo rekening;

    public RekeningenSrvPojo getRekening() {
        return rekening;
    }

    public void setRekening(RekeningenSrvPojo rekening) {
        this.rekening = rekening;
    }

    public String getBeginDatum() {
        return beginDatum;
    }

    public void setBeginDatum(String beginDatum) {
        this.beginDatum = beginDatum;
    }

    public String getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(String eindDatum) {
        this.eindDatum = eindDatum;
    }
    
    public FinanceHeader(){
        SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
        
        Date dateToday = new Date();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToday);
        cal.add(Calendar.MONTH, -1);
        
        Date prevMonth = cal.getTime();
        
        this.beginDatum = dt.format(prevMonth);
        this.eindDatum = dt.format(dateToday);
    }
}

