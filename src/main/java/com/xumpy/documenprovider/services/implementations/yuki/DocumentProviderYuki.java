package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.documenprovider.model.Folder;
import com.xumpy.documenprovider.services.implementations.yuki.model.*;
import com.xumpy.thuisadmin.domain.Documenten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;

@Service
public class DocumentProviderYuki implements DocumentProviderSrv {
    @Autowired YukiBuilder yukiBuilder;
    @Autowired PostToYuki postToYuki;
    @Autowired DocumentProviderDocumentsImpl documentProviderDocuments;

    @Override
    public String getDocumentProviderId() {
        return "YUKI";
    }

    @Override
    public String process(Documenten document) {
        YukiPojo yukiPojo = yukiBuilder.build();

        yukiPojo.setFolder(Folder.UitzoekenYuki);

        if (document.getBedrag().getGroep().getCategory() != null){
            yukiPojo.getFolder().setOrdner(document.getBedrag().getGroep().getCategory());
        }
        yukiPojo.setFileName(document.getBedrag().getOmschrijving().replaceAll(" ", "") + ".pdf");
        yukiPojo.setFile(new ByteArrayInputStream(document.getDocument()));

        try {
            UploadReponse uploadReponse = postToYuki.post(yukiPojo);

            return "Yuki ID: " + uploadReponse.getDocumentID();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Boolean shouldBeProcessed(Documenten document) {
        return documentProviderDocuments.getDocumentProviderDocumentsByDocumentIdAndDocumentProviderId(document.getPk_id(), getDocumentProviderId()).isEmpty();
    }
}
