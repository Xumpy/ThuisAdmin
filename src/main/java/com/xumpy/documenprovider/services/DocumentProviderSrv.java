package com.xumpy.documenprovider.services;

import com.xumpy.thuisadmin.domain.Documenten;

public interface DocumentProviderSrv {
    public Integer getDocumentProviderId();
    public String process(Documenten document);
}
