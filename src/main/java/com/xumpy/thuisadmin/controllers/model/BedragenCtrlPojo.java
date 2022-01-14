package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.domain.*;

import java.math.BigDecimal;
import java.util.Date;

public class BedragenCtrlPojo implements Bedragen {
    private BigDecimal bedrag;
    private Date datum;
    private GroepenCtrlPojo groep;
    private String omschrijving;
    private PersonenCtrlPojo persoon;
    private Integer pk_id;
    private RekeningenCtrlPojo rekening;
    private InvoicesCtrlPojo invoice;
    private BigDecimal taxPercentagePaid;
    private BigDecimal weightAccountancy;
    private Boolean processed;
    private Boolean managedByAccountant;
    private Boolean courant;

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
    public GroepenCtrlPojo getGroep() {
        return groep;
    }

    public void setGroep(GroepenCtrlPojo groep) {
        this.groep = groep;
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public PersonenCtrlPojo getPersoon() {
        return persoon;
    }

    public void setPersoon(PersonenCtrlPojo persoon) {
        this.persoon = persoon;
    }

    @Override
    public Integer getPk_id() {
        return pk_id;
    }

    public void setPk_id(Integer pk_id) {
        this.pk_id = pk_id;
    }

    @Override
    public RekeningenCtrlPojo getRekening() {
        return rekening;
    }

    public void setRekening(RekeningenCtrlPojo rekening) {
        this.rekening = rekening;
    }

    @Override
    public InvoicesCtrlPojo getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoicesCtrlPojo invoice) {
        this.invoice = invoice;
    }

    @Override
    public BigDecimal getTaxPercentagePaid() {
        return taxPercentagePaid;
    }

    public void setTaxPercentagePaid(BigDecimal taxPercentagePaid) {
        this.taxPercentagePaid = taxPercentagePaid;
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

    public BedragenCtrlPojo() {}

    public BedragenCtrlPojo(Bedragen bedragen){
        this.bedrag = bedragen.getBedrag();
        this.datum = bedragen.getDatum();
        this.groep = bedragen.getGroep() != null ? new GroepenCtrlPojo(bedragen.getGroep()) : null;
        this.omschrijving = bedragen.getOmschrijving();
        this.persoon = bedragen.getPersoon() != null ? new PersonenCtrlPojo(bedragen.getPersoon()) : null;
        this.pk_id = bedragen.getPk_id();
        this.rekening = bedragen.getRekening() != null ? new RekeningenCtrlPojo(bedragen.getRekening()) : null;
        this.invoice = bedragen.getInvoice() != null ? new InvoicesCtrlPojo(bedragen.getInvoice()) : null;
        this.taxPercentagePaid = bedragen.getTaxPercentagePaid();
        this.weightAccountancy = bedragen.getWeightAccountancy();
        this.processed = bedragen.getProcessed();
        this.managedByAccountant = bedragen.getManagedByAccountant();
        this.courant = bedragen.getCourant();
    }
}
