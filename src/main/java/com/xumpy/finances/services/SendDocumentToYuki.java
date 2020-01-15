package com.xumpy.finances.services;

import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.thuisadmin.services.DocumentenSrv;
import com.xumpy.thuisadmin.services.model.DocumentenSrvPojo;
import com.xumpy.utilities.yuki.model.Folder;
import com.xumpy.utilities.yuki.model.UploadReponse;
import com.xumpy.utilities.yuki.model.YukiPojo;
import com.xumpy.utilities.yuki.service.PostToYuki;
import com.xumpy.utilities.yuki.service.YukiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class SendDocumentToYuki {
    @Autowired YukiBuilder yukiBuilder;
    @Autowired DocumentenSrv documentenSrv;
    @Autowired PostToYuki postToYuki;

    public Documenten send(Integer documentId) throws IOException, JAXBException {
        Documenten document = documentenSrv.fetchDocument(documentId);

        YukiPojo yukiPojo = yukiBuilder.build();
        yukiPojo.setFolder(Folder.ZelfTeOrdenen);
        if (document.getBedrag().getGroep().getYukiCategory() != null){
            yukiPojo.getFolder().setOrdner(document.getBedrag().getGroep().getYukiCategory());
        }
        yukiPojo.setFileName(document.getBedrag().getOmschrijving().replaceAll(" ", "") + ".pdf");
        yukiPojo.setFile(new ByteArrayInputStream(document.getDocument()));

        UploadReponse uploadReponse = postToYuki.post(yukiPojo);

        DocumentenDaoPojo documentenDaoPojo = new DocumentenDaoPojo(document);
        documentenDaoPojo.setYukiDocumentId(uploadReponse.getDocumentID());

        documentenSrv.update(documentenDaoPojo);

        return documentenDaoPojo;
    }
}
