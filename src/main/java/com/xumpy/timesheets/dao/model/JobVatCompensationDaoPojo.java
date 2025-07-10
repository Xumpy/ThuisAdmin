package com.xumpy.timesheets.dao.model;

import com.xumpy.government.dao.model.VatCompensationDaoPojo;
import com.xumpy.government.domain.VatCompensation;
import com.xumpy.timesheets.domain.JobVatCompensation;
import com.xumpy.timesheets.domain.Jobs;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="TA_JOB_VAT_COMPS")
public class JobVatCompensationDaoPojo implements JobVatCompensation{

    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @ManyToOne
    @JoinColumn(name="FK_JOB_ID")
    private JobsDaoPojo job;

    @ManyToOne
    @JoinColumn(name="FK_VAT_COMPENSATION_ID")
    private VatCompensationDaoPojo vatCompensation;

    @Column(name="AMOUNT")
    private BigDecimal amount;

    @Basic(fetch=FetchType.LAZY)
    @Column(name="DOCUMENT")
    @Lob
    private byte[] document;

    @Column(name="DOCUMENT_NAME")
    private String documentName;

    @Column(name="DOCUMENT_MIME")
    private String documentMime;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public Jobs getJob() {
        return job;
    }

    public void setJob(JobsDaoPojo job) {
        this.job = job;
    }

    @Override
    public VatCompensation getVatCompensation() {
        return vatCompensation;
    }

    public void setVatCompensation(VatCompensationDaoPojo vatCompensation) {
        this.vatCompensation = vatCompensation;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    @Override
    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public String getDocumentMime() {
        return documentMime;
    }

    public void setDocumentMime(String documentMime) {
        this.documentMime = documentMime;
    }

    public JobVatCompensationDaoPojo() {}

    public JobVatCompensationDaoPojo(JobVatCompensation jobVatCompensation){
        this.pkId = jobVatCompensation.getPkId();
        this.job = jobVatCompensation.getJob() == null ? null : new JobsDaoPojo(jobVatCompensation.getJob());
        this.vatCompensation = jobVatCompensation.getVatCompensation() == null ? null : new VatCompensationDaoPojo(jobVatCompensation.getVatCompensation());
        this.amount = jobVatCompensation.getAmount();
        this.document = jobVatCompensation.getDocument();
        this.documentName = jobVatCompensation.getDocumentName();
        this.documentMime = jobVatCompensation.getDocumentMime();
    }
}
