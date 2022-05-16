package com.xumpy.documenprovider.services;

import com.xumpy.documenprovider.model.DPDocument;
import com.xumpy.documenprovider.services.implementations.exceptions.PinNotValidException;
import com.xumpy.documenprovider.services.model.DocumentProviderDocumentsSrvPojo;
import com.xumpy.thuisadmin.domain.Documenten;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DocumentProviderSrv {
    public Integer getDocumentProviderId();
    public String process(Documenten document);

    public String getDumpFromDocumentProvider(Date startDate, Date endDate);
    public String processDumpToBedragAccounting(String dump);

    public List<DPDocument> updateFeedback(Documenten document, Map securityKeys) throws PinNotValidException;
    public void updateAccountingBedragen(DocumentProviderDocumentsSrvPojo document, Map securityKeys) throws PinNotValidException;
}
