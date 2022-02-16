package com.xumpy.thuisadmin.controllers.model;

import com.xumpy.documenprovider.domain.DocumentProviderDump;

import java.util.Date;

public class DocumentProviderDumpCtrlPojo implements DocumentProviderDump {
    private Integer pkId;
    private DocumentProviderCtrlPojo documentProvider;
    private Date startDate;
    private Date endDate;

    @Override
    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    @Override
    public DocumentProviderCtrlPojo getDocumentProvider() {
        return documentProvider;
    }

    public void setDocumentProvider(DocumentProviderCtrlPojo documentProvider) {
        this.documentProvider = documentProvider;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String getDump() {
        return null;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DocumentProviderDumpCtrlPojo(){}

    public DocumentProviderDumpCtrlPojo(DocumentProviderDump documentProviderDump){
        this.pkId = documentProviderDump.getPkId();
        this.documentProvider = new DocumentProviderCtrlPojo(documentProviderDump.getDocumentProvider());
        this.startDate = documentProviderDump.getStartDate();
        this.endDate = documentProviderDump.getEndDate();
    }
}
