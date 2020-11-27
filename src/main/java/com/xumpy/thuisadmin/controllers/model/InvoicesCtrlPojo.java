package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.thuisadmin.domain.Invoices;
import com.xumpy.thuisadmin.domain.Rekeningen;

import java.math.BigDecimal;
import java.util.Date;

public class InvoicesCtrlPojo implements Invoices{
    private Integer pkId;
    private RekeningenCtrlPojo rekening;
    private String invoiceId;
    private Date invoiceDate;
    private Date invoiceDueDate;
    private String invoiceRef;
    private String vatNumber;
    private BigDecimal vatAmount;
    private String description;
    private BigDecimal totalAmount;
    private BigDecimal totalWorkedDays;
    private Boolean closed;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public RekeningenCtrlPojo getRekening() {
        return rekening;
    }

    public void setRekening(RekeningenCtrlPojo rekening) {
        this.rekening = rekening;
    }

    @Override
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    @Override
    public Date getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public void setInvoiceDueDate(Date invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    @Override
    public String getInvoiceRef() {
        return invoiceRef;
    }

    public void setInvoiceRef(String invoiceRef) {
        this.invoiceRef = invoiceRef;
    }

    @Override
    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    @Override
    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    @Override
    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public InvoicesCtrlPojo() { }

    private void setInvoicesProperties(Invoices invoices){
        this.pkId = invoices.getPkId();
        this.description = invoices.getDescription();
        this.vatAmount = invoices.getVatAmount();
        this.vatNumber = invoices.getVatNumber();
        this.invoiceRef = invoices.getInvoiceRef();
        this.invoiceDueDate = invoices.getInvoiceDueDate();
        this.invoiceDate = invoices.getInvoiceDate();
        this.invoiceId = invoices.getInvoiceId();
        this.rekening = invoices.getRekening() != null ? new RekeningenCtrlPojo(invoices.getRekening()) : null;
        this.closed = invoices.getClosed();
    }

    public InvoicesCtrlPojo(Invoices invoices){
        setInvoicesProperties(invoices);
    }

    public BigDecimal getTotalWorkedDays() {
        return totalWorkedDays;
    }

    public void setTotalWorkedDays(BigDecimal totalWorkedDays) {
        this.totalWorkedDays = totalWorkedDays;
    }
}
