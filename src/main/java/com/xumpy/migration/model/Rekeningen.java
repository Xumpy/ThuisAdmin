/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.migration.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author nicom
 */
public class Rekeningen {
    private Integer pk_id;
    private BigDecimal waarde;
    private String naam;
    private Date laatst_bijgewerkt;
    private Integer fk_persoon_id;

    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public BigDecimal getWaarde() {
        return waarde;
    }

    public void setWaarde(BigDecimal waarde) {
        this.waarde = waarde;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Date getLaatst_bijgewerkt() {
        return laatst_bijgewerkt;
    }

    public void setLaatst_bijgewerkt(Date laatst_bijgewerkt) {
        this.laatst_bijgewerkt = laatst_bijgewerkt;
    }

    public Integer getFk_persoon_id() {
        return fk_persoon_id;
    }

    public void setFk_persoon_id(Integer fk_persoon_id) {
        this.fk_persoon_id = fk_persoon_id;
    }
}
