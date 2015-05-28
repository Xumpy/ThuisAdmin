/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.thuisadmin.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author nicom
 */
public class Bedragen {
    private Integer pk_id;
    private Integer fk_type_groep_id;
    private Integer fk_persoon_id;
    private Integer fk_rekening_id;
    private BigDecimal bedrag;
    private Date datum;
    private String omschrijving;

    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public Integer getFk_type_groep_id() {
        return fk_type_groep_id;
    }

    public void setFk_type_groep_id(Integer fk_type_groep_id) {
        this.fk_type_groep_id = fk_type_groep_id;
    }

    public Integer getFk_persoon_id() {
        return fk_persoon_id;
    }

    public void setFk_persoon_id(Integer fk_persoon_id) {
        this.fk_persoon_id = fk_persoon_id;
    }

    public Integer getFk_rekening_id() {
        return fk_rekening_id;
    }

    public void setFk_rekening_id(Integer fk_rekening_id) {
        this.fk_rekening_id = fk_rekening_id;
    }

    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
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
