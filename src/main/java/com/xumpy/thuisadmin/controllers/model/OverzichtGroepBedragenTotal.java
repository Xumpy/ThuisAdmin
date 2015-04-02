/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author Nico
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class OverzichtGroepBedragenTotal implements Serializable{
    private BigDecimal somBedrag;
    private List<OverzichtGroepBedragen> overzichtGroepBedragen;

    @JsonProperty
    public BigDecimal getSomBedrag() {
        return somBedrag;
    }

    public void setSomBedrag(BigDecimal somBedrag) {
        this.somBedrag = somBedrag;
    }

    @JsonProperty
    public List<OverzichtGroepBedragen> getOverzichtGroepBedragen() {
        return overzichtGroepBedragen;
    }

    public void setOverzichtGroepBedragen(List<OverzichtGroepBedragen> overzichtGroepBedragen) {
        this.overzichtGroepBedragen = overzichtGroepBedragen;
    }
}
