package com.xumpy.documenprovider.domain;

import com.xumpy.thuisadmin.domain.Documenten;

import java.util.Date;

public interface DocumentProviderDocuments {
    public Integer getPkId();
    public DocumentProvider getDocumentProvider();
    public Documenten getDocumenten();
    public String getFeedback();
    public Date getDateSent();
}
