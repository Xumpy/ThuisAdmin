/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.model.view;

import com.xumpy.thuisadmin.model.db.Groepen;
import com.xumpy.thuisadmin.model.db.Personen;
import com.xumpy.thuisadmin.model.db.Rekeningen;
import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author Nico
 */
public class NieuwBedrag implements Serializable {
    private Integer pk_id;
    
    private Groepen groep;
    private Personen persoon;
    private Rekeningen rekening;
    private String bedrag;
    private Date datum;
    private String omschrijving;
    
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public Groepen getGroep() {
        return groep;
    }

    public void setGroep(Groepen groep) {
        this.groep = groep;
    }

    public Personen getPersoon() {
        return persoon;
    }

    public void setPersoon(Personen persoon) {
        this.persoon = persoon;
    }

    public Rekeningen getRekening() {
        return rekening;
    }

    public void setRekening(Rekeningen rekening) {
        this.rekening = rekening;
    }

    public String getBedrag() {
        return bedrag;
    }

    public void setBedrag(String bedrag) {
        this.bedrag = bedrag;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}
