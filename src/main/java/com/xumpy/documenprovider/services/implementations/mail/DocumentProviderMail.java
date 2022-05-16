package com.xumpy.documenprovider.services.implementations.mail;

import com.xumpy.documenprovider.model.Folder;
import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.documenprovider.services.implementations.exceptions.PinNotValidException;
import com.xumpy.documenprovider.services.implementations.mail.odata.ExecuteCallExactOnline;
import com.xumpy.documenprovider.model.DPDocument;
import com.xumpy.documenprovider.services.implementations.mail.odata.handler.ExactCookie;
import com.xumpy.thuisadmin.domain.Documenten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DocumentProviderMail implements DocumentProviderSrv {
    @Value( "${mail.verkoopEmail}" )
    private String verkoopEmail;
    @Value( "${mail.aankoopEmail}" )
    private String aankoopEmail;

    @Autowired SmtpEmailBuilder smtpEmailBuilder;
    @Autowired ExecuteCallExactOnline executeCallExactOnline;
    @Autowired ExactCookie exactCookie;

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
    public List<DPDocument> updateFeedback(Documenten document, Map securityKeys) throws PinNotValidException {
        if (securityKeys.get("cookie") != null && !securityKeys.get("cookie").toString().isEmpty()) exactCookie.setCookie(securityKeys.get("cookie").toString());
        if (securityKeys.get("userAgent") != null && !securityKeys.get("userAgent").toString().isEmpty()) executeCallExactOnline.setUserAgent(securityKeys.get("userAgent").toString());

        return executeCallExactOnline.fetchDocumentsByPrice(document.getBedrag());
    }

    @Override
    public void updateAccountingBedragen(Documenten document, Map securityKeys) throws PinNotValidException {
        if (securityKeys.get("cookie") != null && !securityKeys.get("cookie").toString().isEmpty()) exactCookie.setCookie(securityKeys.get("cookie").toString());
        if (securityKeys.get("userAgent") != null && !securityKeys.get("userAgent").toString().isEmpty()) executeCallExactOnline.setUserAgent(securityKeys.get("userAgent").toString());

        throw new RuntimeException("Not yet implemented");
    }
}
