/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.view;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Nico
 */
@Entity
public class OverzichtGroep implements Serializable{
    @Id
    private Integer groepId;

    public Integer getGroepId() {
        return groepId;
    }

    public void setGroepId(Integer groepId) {
        this.groepId = groepId;
    }
    private String naam;
    private Double totaal_opbrengsten;
    private Double totaal_kosten;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
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
