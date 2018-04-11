/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.services.model;

import com.xumpy.thuisadmin.domain.Rekeningen;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author nicom
 */
public class RekeningenSrvPojo implements Rekeningen{
    private Integer pk_id;
    private BigDecimal waarde;
    private String naam;
    private Date laatst_bijgewerkt;
    private PersonenSrvPojo persoon;
    private String bank;
    private String rekeningNr;
    private Boolean closed;

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public BigDecimal getWaarde() {
        return waarde;
    }

    public void setWaarde(BigDecimal waarde) {
        this.waarde = waarde;
    }

    @Override
    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public Date getLaatst_bijgewerkt() {
        return laatst_bijgewerkt;
    }

    public void setLaatst_bijgewerkt(Date laatst_bijgewerkt) {
        this.laatst_bijgewerkt = laatst_bijgewerkt;
    }

    @Override
    public PersonenSrvPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenSrvPojo persoon) {
        this.persoon = persoon;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.pk_id != null ? this.pk_id.hashCode() : 0);
        return hash;
    }

    @Override
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Override
    public String getRekeningNr() {
        return rekeningNr;
    }

    public void setRekeningNr(String rekeningNr) {
        this.rekeningNr = rekeningNr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RekeningenSrvPojo other = (RekeningenSrvPojo) obj;
        if (this.pk_id != other.pk_id && (this.pk_id == null || !this.pk_id.equals(other.pk_id))) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public RekeningenSrvPojo(){
        
    }
    
    public RekeningenSrvPojo(Rekeningen rekeningen){
        this.laatst_bijgewerkt = rekeningen.getLaatst_bijgewerkt();
        this.naam = rekeningen.getNaam();
        this.persoon = rekeningen.getPersoon()!=null ? new PersonenSrvPojo(rekeningen.getPersoon()) : null;
        this.pk_id = rekeningen.getPk_id();
        this.waarde = rekeningen.getWaarde();
        this.rekeningNr = rekeningen.getRekeningNr();
        this.bank = rekeningen.getBank();
        this.closed = rekeningen.getClosed();
    }
}
