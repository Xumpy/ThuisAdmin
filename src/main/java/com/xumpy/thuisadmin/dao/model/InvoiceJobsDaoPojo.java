package com.xumpy.thuisadmin.dao.model;

import com.xumpy.thuisadmin.domain.InvoiceJobs;
import com.xumpy.timesheets.dao.model.JobsDaoPojo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="TA_JOBS_INVOICES")
public class InvoiceJobsDaoPojo implements InvoiceJobs{
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;
    @ManyToOne
    @JoinColumn(name="FK_JOB_ID")
    private JobsDaoPojo job;
    @ManyToOne
    @JoinColumn(name="FK_INVOICE_ID")
    private InvoicesDaoPojo invoice;
    @Column(name="AMOUNT")
    private BigDecimal amount;
    @Column(name="DESCRIPTION")
    private String description;
    @Column(name="TIME_UNIT_DAYS")
    private Boolean timeUnitDays;

    public Boolean isTimeUnitDays() {
        return timeUnitDays;
    }

    public void setTimeUnitDays(Boolean timeUnitDays) {
        this.timeUnitDays = timeUnitDays;
    }

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public JobsDaoPojo getJob() {
        return job;
    }

    public void setJob(JobsDaoPojo job) {
        this.job = job;
    }

    public InvoicesDaoPojo getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoicesDaoPojo invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InvoiceJobsDaoPojo() {}
    public InvoiceJobsDaoPojo(InvoiceJobs invoiceJobs) {
        this.pkId = invoiceJobs.getPkId();
        this.job = invoiceJobs.getJob() != null ? new JobsDaoPojo(invoiceJobs.getJob()) : null;
        this.invoice = invoiceJobs.getInvoice() != null ? new InvoicesDaoPojo(invoiceJobs.getInvoice()) : null;
        this.amount = invoiceJobs.getAmount();
        this.description = invoiceJobs.getDescription();
        this.timeUnitDays = invoiceJobs.isTimeUnitDays();
    }
}
