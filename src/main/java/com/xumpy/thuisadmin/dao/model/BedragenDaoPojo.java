/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.dao.model;


import com.xumpy.thuisadmin.domain.Bedragen;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Nico
 */
@Entity
@Table(name="TA_BEDRAGEN")
public class BedragenDaoPojo implements Serializable, Bedragen{
    @Id
    @Column(name="PK_ID")
    private Integer pk_id;

    @ManyToOne
    @JoinColumn(name="FK_TYPE_GROEP_ID")
    @NotNull
    private GroepenDaoPojo groep;
    
    @ManyToOne
    @JoinColumn(name="FK_PERSOON_ID")
    @NotNull
    private PersonenDaoPojo persoon;
    
    @ManyToOne
    @JoinColumn(name="FK_REKENING_ID")
    @NotNull
    private RekeningenDaoPojo rekening;

    @ManyToOne
    @JoinColumn(name="FK_INVOICE_ID")
    private InvoicesDaoPojo invoice;

    @Column(name="BEDRAG")
    @NotNull
    private BigDecimal bedrag;
    
    @Column(name="DATUM")
    @NotNull
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date datum;
    
    @Column(name="OMSCHRIJVING")
    private String omschrijving;

    @Column(name="TAX_PERC_PAID")
    private BigDecimal taxPercentagePaid;

    @Column(name="WEIGHT_ACC")
    private BigDecimal weightAccountancy;

    @Column(name="PROCESSED")
    private Boolean processed;

    @Column(name="MANAGED_BY_ACC", nullable = false)
    private Boolean managedByAccountant;

    @Column(name="COURANT", nullable = false)
    private Boolean courant;

    @Override
    public Boolean getManagedByAccountant() {
        return managedByAccountant;
    }

    public void setManagedByAccountant(Boolean managedByAccountant) {
        this.managedByAccountant = managedByAccountant;
    }

    @Override
    public Boolean getCourant() {
        return courant;
    }

    public void setCourant(Boolean courant) {
        this.courant = courant;
    }

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public RekeningenDaoPojo getRekening() {
        return rekening;
    }

    public void setRekening(RekeningenDaoPojo rekening) {
        this.rekening = rekening;
    }

    @Override
    public BigDecimal getBedrag() {
        return bedrag;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    @Override
    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public GroepenDaoPojo getGroep() {
        return groep;
    }

    public void setGroep(GroepenDaoPojo groep) {
        this.groep = groep;
    }

    @Override
    public PersonenDaoPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenDaoPojo persoon) {
        this.persoon = persoon;
    }
    
    public BedragenDaoPojo(){
        
    }

    @Override
    public BigDecimal getTaxPercentagePaid() {
        return taxPercentagePaid;
    }

    public void setTaxPercentagePaid(BigDecimal taxPercentagePaid) {
        this.taxPercentagePaid = taxPercentagePaid;
    }

    @Override
    public InvoicesDaoPojo getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoicesDaoPojo invoice) {
        this.invoice = invoice;
    }

    @Override
    public BigDecimal getWeightAccountancy() {
        return weightAccountancy;
    }

    public void setWeightAccountancy(BigDecimal weightAccountancy) {
        this.weightAccountancy = weightAccountancy;
    }

    @Override
    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public BedragenDaoPojo(Bedragen bedragen){
        this.bedrag = bedragen.getBedrag();
        this.datum = bedragen.getDatum();
        this.groep = bedragen.getGroep()!=null ? new GroepenDaoPojo(bedragen.getGroep()): null;
        this.omschrijving = bedragen.getOmschrijving();
        this.persoon = bedragen.getPersoon()!=null ? new PersonenDaoPojo(bedragen.getPersoon()) : null;
        this.pk_id = bedragen.getPk_id();
        this.rekening = bedragen.getRekening()!=null ? new RekeningenDaoPojo(bedragen.getRekening()) : null;
        this.taxPercentagePaid = bedragen.getTaxPercentagePaid();
        this.invoice = bedragen.getInvoice() != null ? new InvoicesDaoPojo(bedragen.getInvoice()) : null;
        this.weightAccountancy = bedragen.getWeightAccountancy();
        this.processed = bedragen.getProcessed();
        this.managedByAccountant = bedragen.getManagedByAccountant();
        this.courant = bedragen.getCourant();
    }
}
