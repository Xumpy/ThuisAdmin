package com.xumpy.documenprovider.services;

import com.xumpy.documenprovider.model.DPDocument;
import com.xumpy.documenprovider.services.implementations.exceptions.PinNotValidException;
import com.xumpy.thuisadmin.domain.Documenten;

import java.util.Date;
import java.util.List;

public interface DocumentProviderSrv {
    public Integer getDocumentProviderId();
    public String process(Documenten document);

    public String getDumpFromDocumentProvider(Date startDate, Date endDate);
    public String processDumpToBedragAccounting(String dump);

    public List<DPDocument> updateFeedback(Documenten document, String pincode) throws PinNotValidException;
    public void updateAccountingBedragen(Documenten document, String pincode) throws PinNotValidException;
}
