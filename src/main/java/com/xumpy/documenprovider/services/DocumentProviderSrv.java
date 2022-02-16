package com.xumpy.documenprovider.services;

import com.xumpy.thuisadmin.domain.Documenten;

import java.util.Date;

public interface DocumentProviderSrv {
    public Integer getDocumentProviderId();
    public String process(Documenten document);

    public String getDumpFromDocumentProvider(Date startDate, Date endDate);
    public String processDumpToBedragAccounting(String dump);
}
