package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.domain.BedragAccounting;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="TA_BEDRAG_ACCOUNTING")
public class BedragAccountingDaoPojo implements BedragAccounting {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @ManyToOne
    @JoinColumn(name="FK_BEDRAG_ID")
    @NotNull
    private BedragenDaoPojo bedrag;

    @Column(name="DATUM")
    @NotNull
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date datum;

    @Column(name="BEDRAG")
    @NotNull
    private BigDecimal accountBedrag;

    @Column(name="ACCOUNT_CODE")
    private String accountCode;

    @Column(name="OMSCHRIJVING")
    private String omschrijving;

    @Column(name="CUSTOMER_NAME")
    private String customerName;

    @Column(name="VAT_NUMBER")
    private String vatNumber;

    @Column(name="TAX_DESCRIPTION")
    private String taxDescription;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public BedragenDaoPojo getBedrag() {
        return bedrag;
    }

    public void setBedrag(BedragenDaoPojo bedrag) {
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

    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }

    public BedragAccountingDaoPojo() {}

    public BedragAccountingDaoPojo(BedragAccounting bedragAccounting){
        this.pkId = bedragAccounting.getPkId();
        this.bedrag = new BedragenDaoPojo(bedragAccounting.getBedrag());
        this.datum = bedragAccounting.getDatum();
        this.accountBedrag = bedragAccounting.getAccountBedrag();
        this.accountCode = bedragAccounting.getAccountCode();
        this.omschrijving = bedragAccounting.getOmschrijving();
        this.customerName = bedragAccounting.getCustomerName();
        this.vatNumber = bedragAccounting.getVatNumber();
        this.taxDescription = bedragAccounting.getTaxDescription();
    }
}
