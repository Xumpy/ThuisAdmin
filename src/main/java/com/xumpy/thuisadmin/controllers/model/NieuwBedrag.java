/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.services.model.GroepenSrvPojo;
import com.xumpy.thuisadmin.services.model.PersonenSrvPojo;
import com.xumpy.thuisadmin.services.model.RekeningenSrvPojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * @author Nico
 */
public class NieuwBedrag implements Serializable {
    private Integer pk_id;
    
    private GroepenSrvPojo groep;
    private PersonenSrvPojo persoon;
    private RekeningenSrvPojo rekening;
    private String bedrag;
    private Date datum;
    private String omschrijving;
    private Boolean excludeAccountancy;
    private BigDecimal taxPercentagePaid;
    private InvoicesCtrlPojo invoice;
    private BigDecimal weightAccountancy;
    private Boolean processed;
    private Boolean managedByAccountant;
    private Boolean courrant;

    public Boolean getManagedByAccountant() {
        return managedByAccountant;
    }

    public void setManagedByAccountant(Boolean managedByAccountant) {
        this.managedByAccountant = managedByAccountant;
    }

    public Boolean getCourrant() {
        return courrant;
    }

    public void setCourrant(Boolean courrant) {
        this.courrant = courrant;
    }

    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    public GroepenSrvPojo getGroep() {
        return groep;
    }

    public void setGroep(GroepenSrvPojo groep) {
        this.groep = groep;
    }

    public PersonenSrvPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenSrvPojo persoon) {
        this.persoon = persoon;
    }

    public RekeningenSrvPojo getRekening() {
        return rekening;
    }

    public void setRekening(RekeningenSrvPojo rekening) {
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

    public Boolean getExcludeAccountancy() {
        return excludeAccountancy;
    }

    public void setExcludeAccountancy(Boolean excludeAccountancy) {
        this.excludeAccountancy = excludeAccountancy;
    }

    public BigDecimal getTaxPercentagePaid() {
        return taxPercentagePaid;
    }

    public void setTaxPercentagePaid(BigDecimal taxPercentagePaid) {
        this.taxPercentagePaid = taxPercentagePaid;
    }

    public InvoicesCtrlPojo getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoicesCtrlPojo invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getWeightAccountancy() {
        return weightAccountancy;
    }

    public void setWeightAccountancy(BigDecimal weightAccountancy) {
        this.weightAccountancy = weightAccountancy;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }
}
