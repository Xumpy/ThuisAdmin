package com.xumpy.documenprovider.dao.model;

import com.xumpy.documenprovider.domain.DocumentProviderValid;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="TA_DOCUMENT_PROVIDER_VALID")
public class DocumentProviderValidDaoPojo implements DocumentProviderValid {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @ManyToOne
    @JoinColumn(name="FK_DOCUMENT_PROVIDER_ID")
    @NotNull
    private DocumentProviderDaoPojo documentProvider;

    @Column(name="DATE_FROM")
    @NotNull
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date dateFrom;

    @Column(name="DATE_UNTIL")
    @NotNull
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date dateUntil;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public Date getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(Date dateUntil) {
        this.dateUntil = dateUntil;
    }

    public DocumentProviderDaoPojo getDocumentProvider() {
        return documentProvider;
    }

    public void setDocumentProvider(DocumentProviderDaoPojo documentProvider) {
        this.documentProvider = documentProvider;
    }

    public DocumentProviderValidDaoPojo(){}

    public DocumentProviderValidDaoPojo(DocumentProviderValid documentProviderValid){
        this.pkId = documentProviderValid.getPkId();
        this.documentProvider = documentProviderValid.getDocumentProvider() != null ? new DocumentProviderDaoPojo(documentProviderValid.getDocumentProvider()) : null;
        this.dateFrom = documentProviderValid.getDateFrom();
        this.dateUntil = documentProviderValid.getDateUntil();
    }
}
