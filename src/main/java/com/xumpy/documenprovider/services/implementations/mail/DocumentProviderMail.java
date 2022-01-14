package com.xumpy.documenprovider.services.implementations.mail;

import com.xumpy.documenprovider.model.Folder;
import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.thuisadmin.domain.Documenten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DocumentProviderMail implements DocumentProviderSrv {
    @Value( "${mail.verkoopEmail}" )
    private String verkoopEmail;
    @Value( "${mail.aankoopEmail}" )
    private String aankoopEmail;

    @Autowired SmtpEmailBuilder smtpEmailBuilder;

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
}
