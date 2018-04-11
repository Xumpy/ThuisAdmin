package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.domain.Personen;
import com.xumpy.thuisadmin.domain.Rekeningen;

import java.math.BigDecimal;
import java.util.Date;

public class RekeningenCtrlPojo implements Rekeningen{
    private Integer pk_id;
    private BigDecimal waarde;
    private String naam;
    private Date laatst_bijgewerkt;
    private PersonenCtrlPojo persoon;
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
    public PersonenCtrlPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenCtrlPojo persoon) {
        this.persoon = persoon;
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

    @Override
    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public void setRekeningNr(String rekeningNr) {
        this.rekeningNr = rekeningNr;
    }

    public RekeningenCtrlPojo() {}
    public RekeningenCtrlPojo(Rekeningen rekeningen) {
        this.laatst_bijgewerkt = rekeningen.getLaatst_bijgewerkt();
        this.naam = rekeningen.getNaam();
        this.persoon = rekeningen.getPersoon() != null ? new PersonenCtrlPojo(rekeningen.getPersoon()) : null;
        this.pk_id = rekeningen.getPk_id();
        this.waarde = rekeningen.getWaarde();
        this.rekeningNr = rekeningen.getRekeningNr();
        this.bank = rekeningen.getBank();
        this.closed = rekeningen.getClosed();
    }
}
