package com.xumpy.finances.services;

import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.dao.model.DocumentProviderDocumentsDaoPojo;
import com.xumpy.documenprovider.domain.DocumentProvider;
import com.xumpy.thuisadmin.domain.Documenten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired DocumentProviderDocumentsImpl documentProviderDocumentsImpl;

    public Boolean isDocumentSentToDocumentProvider(Documenten document, DocumentProvider documentProvider){
        List<DocumentProviderDocumentsDaoPojo> documentProviderDocumentsDaoPojos = documentProviderDocumentsImpl.getDocumentProviderDocumentsByDocumentId(document.getPk_id());
        for(DocumentProviderDocumentsDaoPojo documentProviderDocumentsDaoPojo: documentProviderDocumentsDaoPojos){
            if (documentProviderDocumentsDaoPojo.getDocumentProvider().getPkId().equals(documentProvider.getPkId())){
                return true;
            }
        }
        return false;
    }
}
