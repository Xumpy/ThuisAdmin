/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model.graphiek;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Nico
 */
public class OverzichtBedrag implements Serializable{
    private Date datum;
    private BigDecimal bedrag;

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }
    
    public OverzichtBedrag(Date datum, BigDecimal bedrag, Integer negatief){
        this.datum = datum;
        
        if (negatief != null && negatief.equals(1)){
            this.bedrag = (bedrag.multiply(new BigDecimal(-1)));
        } else {
            this.bedrag = bedrag;
        }
    }
}
