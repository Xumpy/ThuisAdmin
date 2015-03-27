/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.view;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author Nico
 */

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class OverzichtGroepBedragenTotal implements Serializable{
    private BigDecimal somBedrag;
    private List<OverzichtGroepBedragen> overzichtGroepBedragen;

    public BigDecimal getSomBedrag() {
        return somBedrag;
    }

    public void setSomBedrag(BigDecimal somBedrag) {
        this.somBedrag = somBedrag;
    }

    public List<OverzichtGroepBedragen> getOverzichtGroepBedragen() {
        return overzichtGroepBedragen;
    }

    public void setOverzichtGroepBedragen(List<OverzichtGroepBedragen> overzichtGroepBedragen) {
        this.overzichtGroepBedragen = overzichtGroepBedragen;
    }
    
    public OverzichtGroepBedragenTotal(){
        
    }
}
