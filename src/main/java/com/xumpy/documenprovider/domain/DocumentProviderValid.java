package com.xumpy.documenprovider.domain;

import java.util.Date;

public interface DocumentProviderValid {
    public Integer getPkId();
    public DocumentProvider getDocumentProvider();
    public Date getDateFrom();
    public Date getDateUntil();
}
