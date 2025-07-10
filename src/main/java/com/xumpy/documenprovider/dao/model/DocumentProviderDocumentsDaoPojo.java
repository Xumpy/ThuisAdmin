package com.xumpy.documenprovider.dao.model;

import com.xumpy.documenprovider.domain.DocumentProviderDocuments;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.domain.Documenten;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="TA_DOCUMENT_PROVIDER_DOCUMENTS")
public class DocumentProviderDocumentsDaoPojo implements DocumentProviderDocuments {
    @Id
    @Column(name="PK_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer pkId;

    @ManyToOne
    @JoinColumn(name="FK_DOCUMENT_PROVIDER_ID")
    @NotNull
    private DocumentProviderDaoPojo documentProvider;

    @ManyToOne
    @JoinColumn(name="FK_BEDRAG_DOCUMENTEN_ID")
    @NotNull
    private DocumentenDaoPojo documenten;

    @Column(name="FEEDBACK")
    private String feedback;

    @Column(name="DATE_SENT")
    @NotNull
    @Temporal(jakarta.persistence.TemporalType.DATE)
    private Date dateSent;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public DocumentProviderDaoPojo getDocumentProvider() {
        return documentProvider;
    }

    public void setDocumentProvider(DocumentProviderDaoPojo documentProvider) {
        this.documentProvider = documentProvider;
    }

    @Override
    public Documenten getDocumenten() {
        return documenten;
    }

    public void setDocumenten(DocumentenDaoPojo documenten) {
        this.documenten = documenten;
    }

    @Override
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public DocumentProviderDocumentsDaoPojo(){}

    public DocumentProviderDocumentsDaoPojo(DocumentProviderDocuments documentProviderDocuments){
        this.pkId = documentProviderDocuments.getPkId();
        this.documentProvider = documentProviderDocuments.getDocumentProvider() != null ? new DocumentProviderDaoPojo(documentProviderDocuments.getDocumentProvider()) : null;
        this.documenten = documentProviderDocuments.getDocumenten() != null ? new DocumentenDaoPojo(documentProviderDocuments.getDocumenten()) : null;
        this.feedback = documentProviderDocuments.getFeedback();
        this.dateSent = documentProviderDocuments.getDateSent();
    }
}
