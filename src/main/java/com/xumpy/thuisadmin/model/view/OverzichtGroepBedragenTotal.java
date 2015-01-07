/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.view;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Nico
 */
public class OverzichtGroepBedragenTotal {
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
}
