package com.xumpy.documenprovider.services.implementations.mail;

import com.xumpy.documenprovider.model.Folder;
import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.documenprovider.services.implementations.exceptions.PinNotValidException;
import com.xumpy.documenprovider.services.implementations.mail.odata.ExecuteCallExactOnline;
import com.xumpy.documenprovider.model.DPDocument;
import com.xumpy.thuisadmin.domain.Documenten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DocumentProviderMail implements DocumentProviderSrv {
    @Value( "${mail.verkoopEmail}" )
    private String verkoopEmail;
    @Value( "${mail.aankoopEmail}" )
    private String aankoopEmail;

    @Autowired SmtpEmailBuilder smtpEmailBuilder;
    @Autowired ExecuteCallExactOnline executeCallExactOnline;

    @Override
    public Integer getDocumentProviderId() {
        return 2;
    }

    private Boolean isVerkoopDocument(Documenten document){
        return Folder.Verkoop.getOrdner().equals(document.getBedrag().getGroep().getCategory());
    }

    @Override
    public String process(Documenten document) {
        String email = isVerkoopDocument(document) ? verkoopEmail : aankoopEmail;

        smtpEmailBuilder.sendSimpleMessage(email, document.getBedrag().getOmschrijving(), "", document);

        return "Mail Sent To: " + email;
    }

    @Override
    public String getDumpFromDocumentProvider(Date startDate, Date endDate) {
        throw new RuntimeException("Not Implemented To Get Dump From Document Provider");
    }

    @Override
    public String processDumpToBedragAccounting(String dump) {
        throw new RuntimeException("Not Implemented To Process Dump From Document Provider");
    }

    @Override
    public List<DPDocument> updateFeedback(Documenten document, String pincode) throws PinNotValidException {
        executeCallExactOnline.setAuthKey(pincode);

        return executeCallExactOnline.fetchDocumentsByPrice(document.getBedrag());
    }

    @Override
    public void updateAccountingBedragen(Documenten document, String pincode) throws PinNotValidException {
        executeCallExactOnline.setAuthKey(pincode);

        throw new RuntimeException("Not yet implemented");
    }
}
