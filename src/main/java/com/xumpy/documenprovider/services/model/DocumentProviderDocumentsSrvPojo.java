package com.xumpy.documenprovider.services.model;

import com.xumpy.documenprovider.domain.DocumentProviderDocuments;
import com.xumpy.thuisadmin.services.model.DocumentenSrvPojo;

import java.util.Date;

public class DocumentProviderDocumentsSrvPojo implements DocumentProviderDocuments {
    private Integer pkId;
    private DocumentProviderSrvPojo documentProvider;
    private DocumentenSrvPojo documenten;
    private String feedback;
    private Date dateSent;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public DocumentProviderSrvPojo getDocumentProvider() {
        return documentProvider;
    }

    public void setDocumentProvider(DocumentProviderSrvPojo documentProvider) {
        this.documentProvider = documentProvider;
    }

    @Override
    public DocumentenSrvPojo getDocumenten() {
        return documenten;
    }

    public void setDocumenten(DocumentenSrvPojo documenten) {
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

    public DocumentProviderDocumentsSrvPojo(){}

    public DocumentProviderDocumentsSrvPojo(DocumentProviderDocuments documentProviderDocuments){
        this.pkId = documentProviderDocuments.getPkId();
        this.documentProvider = documentProviderDocuments.getDocumentProvider() != null ? new DocumentProviderSrvPojo(documentProviderDocuments.getDocumentProvider()) : null;
        this.documenten = documentProviderDocuments.getDocumenten() != null ? new DocumentenSrvPojo(documentProviderDocuments.getDocumenten()) : null;
        this.feedback = documentProviderDocuments.getFeedback();
        this.dateSent = documentProviderDocuments.getDateSent();
    }
}
