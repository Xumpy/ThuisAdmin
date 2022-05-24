package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.domain.BedragAccounting;
import com.xumpy.thuisadmin.domain.Groepen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BedragAccountingCtrlPojo implements BedragAccounting {
    private Integer pkId;
    private BedragenCtrlPojo bedrag;
    private Date datum;
    private BigDecimal accountBedrag;
    private String accountCode;
    private String omschrijving;
    private String customerName;
    private String vatNumber;
    private String taxDescription;
    private List<GroepenCtrlPojo> linkedGroepenByAccountCode;
    private Boolean correctGroep;
    private Boolean needsToMove;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public BedragenCtrlPojo getBedrag() {
        return bedrag;
    }

    public void setBedrag(BedragenCtrlPojo bedrag) {
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
    public BigDecimal getAccountBedrag() {
        return accountBedrag;
    }

    public void setAccountBedrag(BigDecimal accountBedrag) {
        this.accountBedrag = accountBedrag;
    }

    @Override
    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    @Override
    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @Override
    public String getTaxDescription() {
        return taxDescription;
    }

    public List<GroepenCtrlPojo> getLinkedGroepenByAccountCode() {
        return linkedGroepenByAccountCode;
    }

    public void setLinkedGroepenByAccountCode(List<GroepenCtrlPojo> linkedGroepenByAccountCode) {
        this.linkedGroepenByAccountCode = linkedGroepenByAccountCode;
    }

    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }

    public BedragAccountingCtrlPojo() {}

    public Boolean getCorrectGroep() {
        return correctGroep;
    }

    public void setCorrectGroep(Boolean correctGroep) {
        this.correctGroep = correctGroep;
    }

    public Boolean getNeedsToMove() {
        return needsToMove;
    }

    public void setNeedsToMove(Boolean needsToMove) {
        this.needsToMove = needsToMove;
    }

    private List<GroepenCtrlPojo> groepenToGroepenCtrlList(List<Groepen> groepen){
        List<GroepenCtrlPojo> groepenCtrlPojos = new ArrayList<>();
        for(Groepen groep: groepen){
            groepenCtrlPojos.add(new GroepenCtrlPojo(groep));
        }

        return  groepenCtrlPojos;
    }

    private Boolean needsToMove(List<GroepenCtrlPojo> linkedGroepenByAccountCode, Groepen bedragGroep){
        if (this.linkedGroepenByAccountCode.size() > 0){
            for(Groepen groep: linkedGroepenByAccountCode){
                if (groep.getPk_id().equals(bedragGroep.getPk_id())){
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private Boolean correctGroep(List<GroepenCtrlPojo> linkedGroepenByAccountCode, Groepen bedragGroep){
        if (this.linkedGroepenByAccountCode.size() > 0){
            for(Groepen groep: linkedGroepenByAccountCode){
                if (groep.getPk_id().equals(bedragGroep.getPk_id())){
                    return true;
                }
            }
        }
        return false;
    }

    public BedragAccountingCtrlPojo(BedragAccounting bedragAccounting, List<Groepen> groepenByAccountCode){
        this.pkId = bedragAccounting.getPkId();
        this.bedrag = new BedragenCtrlPojo(bedragAccounting.getBedrag());
        this.datum = bedragAccounting.getDatum();
        this.accountBedrag = bedragAccounting.getAccountBedrag();
        this.accountCode = bedragAccounting.getAccountCode();
        this.omschrijving = bedragAccounting.getOmschrijving();
        this.customerName = bedragAccounting.getCustomerName();
        this.vatNumber = bedragAccounting.getVatNumber();
        this.taxDescription = bedragAccounting.getTaxDescription();
        this.linkedGroepenByAccountCode = groepenToGroepenCtrlList(groepenByAccountCode);
        this.needsToMove = needsToMove(this.linkedGroepenByAccountCode, this.bedrag.getGroep());
        this.correctGroep = correctGroep(this.linkedGroepenByAccountCode, this.bedrag.getGroep());
    }
}
