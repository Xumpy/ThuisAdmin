package com.xumpy.documenprovider.controllers.model;

import com.xumpy.documenprovider.domain.DocumentProviderDocuments;
import com.xumpy.thuisadmin.controllers.model.DocumentenCtrlPojo;

import java.util.Date;

public class DocumentProviderDocumentsCtrlPojo implements DocumentProviderDocuments {
    private Integer pkId;
    private String documentProviderId;
    private DocumentenCtrlPojo documenten;
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
    public String getDocumentProviderId() {
        return documentProviderId;
    }

    public void setDocumentProviderId(String documentProviderId) {
        this.documentProviderId = documentProviderId;
    }

    @Override
    public DocumentenCtrlPojo getDocumenten() {
        return documenten;
    }

    public void setDocumenten(DocumentenCtrlPojo documenten) {
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

    public DocumentProviderDocumentsCtrlPojo(){}

    public DocumentProviderDocumentsCtrlPojo(DocumentProviderDocuments documentProviderDocuments){
        this.pkId = documentProviderDocuments.getPkId();
        this.documentProviderId = documentProviderDocuments.getDocumentProviderId();
        this.documenten = documentProviderDocuments.getDocumenten() != null ? new DocumentenCtrlPojo(documentProviderDocuments.getDocumenten()) : null;
        this.feedback = documentProviderDocuments.getFeedback();
        this.dateSent = documentProviderDocuments.getDateSent();
    }
}
