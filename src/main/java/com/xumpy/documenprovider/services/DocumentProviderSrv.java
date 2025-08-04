package com.xumpy.documenprovider.services;

import com.xumpy.thuisadmin.domain.Documenten;

public interface DocumentProviderSrv {
    public String getDocumentProviderId();
    public String process(Documenten document);
    public Boolean shouldBeProcessed(Documenten document);
}
