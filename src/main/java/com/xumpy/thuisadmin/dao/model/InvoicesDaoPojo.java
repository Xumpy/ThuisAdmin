package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.domain.Invoices;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="TA_INVOICES")
public class InvoicesDaoPojo implements Invoices{
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;
    @ManyToOne
    @JoinColumn(name="FK_REKENING_ID")
    private RekeningenDaoPojo rekening;
    @Column(name="INVOICE_ID")
    private String invoiceId;
    @Column(name="INVOICE_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date invoiceDate;
    @Column(name="INVOICE_DUE_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date invoiceDueDate;
    @Column(name="INVOICE_REF")
    private String invoiceRef;
    @Column(name="VAT_NUMBER")
    private String vatNumber;
    @Column(name="VAT_AMOUNT")
    private BigDecimal vatAmount;
    @Column(name="DESCRIPTION")
    private String description;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public RekeningenDaoPojo getRekening() {
        return rekening;
    }

    public void setRekening(RekeningenDaoPojo rekening) {
        this.rekening = rekening;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public void setInvoiceDueDate(Date invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    public String getInvoiceRef() {
        return invoiceRef;
    }

    public void setInvoiceRef(String invoiceRef) {
        this.invoiceRef = invoiceRef;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InvoicesDaoPojo(){ }
    public InvoicesDaoPojo(Invoices invoices){
        this.pkId = invoices.getPkId();
        this.description = invoices.getDescription();
        this.vatAmount = invoices.getVatAmount();
        this.vatNumber = invoices.getVatNumber();
        this.invoiceRef = invoices.getInvoiceRef();
        this.invoiceDueDate = invoices.getInvoiceDueDate();
        this.invoiceDate = invoices.getInvoiceDate();
        this.invoiceId = invoices.getInvoiceId();
        this.rekening = invoices.getRekening() != null ? new RekeningenDaoPojo(invoices.getRekening()) : null;
    }
}
