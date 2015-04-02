/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Nico
 */
public class FinanceOverzichtGroep implements Serializable {
    private Double totaal_opbrengsten;
    private Double totaal_kosten;
    private List<OverzichtGroep> overzichtGroep;

    public List<OverzichtGroep> getOverzichtGroep() {
        return overzichtGroep;
    }

    public void setOverzichtGroep(List<OverzichtGroep> overzichtGroep) {
        this.overzichtGroep = overzichtGroep;
    }

    public Double getTotaal_opbrengsten() {
        return totaal_opbrengsten;
    }

    public void setTotaal_opbrengsten(Double totaal_opbrengsten) {
        this.totaal_opbrengsten = totaal_opbrengsten;
    }

    public Double getTotaal_kosten() {
        return totaal_kosten;
    }

    public void setTotaal_kosten(Double totaal_kosten) {
        this.totaal_kosten = totaal_kosten;
    }
}
