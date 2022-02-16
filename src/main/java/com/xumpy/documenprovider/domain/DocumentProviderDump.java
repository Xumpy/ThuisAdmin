package com.xumpy.documenprovider.domain;

import java.util.Date;

public interface DocumentProviderDump {
    public Integer getPkId();
    public DocumentProvider getDocumentProvider();
    public Date getStartDate();
    public Date getEndDate();
    public String getDump();
}
