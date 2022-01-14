package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.documenprovider.model.Folder;
import com.xumpy.documenprovider.services.implementations.yuki.model.UploadReponse;
import com.xumpy.documenprovider.services.implementations.yuki.model.YukiPojo;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.thuisadmin.services.DocumentenSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;

@Service
public class DocumentProviderYuki implements DocumentProviderSrv {
    @Autowired YukiBuilder yukiBuilder;
    @Autowired DocumentenSrv documentenSrv;
    @Autowired PostToYuki postToYuki;

    @Override
    public Integer getDocumentProviderId() {
        return 1;
    }

    @Override
    public String process(Documenten document) {
        YukiPojo yukiPojo = yukiBuilder.build();
        yukiPojo.setFolder(Folder.ZelfTeOrdenen);
        if (document.getBedrag().getGroep().getYukiCategory() != null){
            yukiPojo.getFolder().setOrdner(document.getBedrag().getGroep().getYukiCategory());
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
}
