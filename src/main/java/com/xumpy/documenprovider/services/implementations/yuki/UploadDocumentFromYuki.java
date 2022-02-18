package com.xumpy.documenprovider.services.implementations.yuki;

import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.dao.implementations.DocumentProviderImpl;
import com.xumpy.documenprovider.dao.model.DocumentProviderDaoPojo;
import com.xumpy.documenprovider.dao.model.DocumentProviderDocumentsDaoPojo;
import com.xumpy.documenprovider.services.implementations.yuki.model.TransactionDocument;
import com.xumpy.documenprovider.services.implementations.yuki.model.TransactionInfo;
import com.xumpy.thuisadmin.dao.implementations.DocumentenDaoImpl;
import com.xumpy.thuisadmin.dao.model.BedragenDaoPojo;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.domain.Bedragen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

@Service
public class UploadDocumentFromYuki {
    @Autowired DocumentProviderYuki documentProviderYuki;
    @Autowired DocumentProviderImpl documentProvider;
    @Autowired DocumentProviderDocumentsImpl documentProviderDocuments;
    @Autowired DocumentenDaoImpl documentenDao;

    private String determineMimeType(String filename){
        if (filename.endsWith(".pdf")){
            return "application/pdf";
        }
        if (filename.endsWith(".jpg")){
            return "image/jpeg";
        }
        return "application/octet-stream";
    }

    private byte[] convertBase64Document(String data) throws UnsupportedEncodingException {
        return Base64.getDecoder().decode(data.getBytes("UTF-8"));
    }

    private Integer determinePrio(Bedragen bedrag){
        Integer prio = 1;
        List<DocumentenDaoPojo> documentenDaoPojoList = documentenDao.fetchDocumentByBedrag(bedrag.getPk_id());

        for(DocumentenDaoPojo documentenDaoPojo: documentenDaoPojoList){
            if (documentenDaoPojo.getPrio() >= prio){
                prio = documentenDaoPojo.getPrio() + 1;
            }
        }

        return prio;
    }

    private DocumentenDaoPojo createDocumentenDaoPojo(TransactionInfo transactionInfo, TransactionDocument transactionDocument, Bedragen bedrag) throws UnsupportedEncodingException {
        DocumentenDaoPojo documentenDaoPojo = new DocumentenDaoPojo();
        documentenDaoPojo.setPk_id(documentenDao.getNewPkId());
        documentenDaoPojo.setBedrag(new BedragenDaoPojo(bedrag));
        documentenDaoPojo.setOmschrijving(transactionInfo.getDescription());
        documentenDaoPojo.setDocument(convertBase64Document(transactionDocument.getFiledata()));
        documentenDaoPojo.setDocument_naam(transactionDocument.getFileName());
        documentenDaoPojo.setDocument_mime(determineMimeType(transactionDocument.getFileName()));
        documentenDaoPojo.setPrio(determinePrio(bedrag));

        return documentenDao.save(documentenDaoPojo);
    }

    public void upload(TransactionInfo transactionInfo, TransactionDocument transactionDocument, Bedragen bedrag) throws UnsupportedEncodingException {
        DocumentProviderDaoPojo documentProviderDaoPojo = documentProvider.findById(documentProviderYuki.getDocumentProviderId()).get();
        DocumentenDaoPojo documentenDaoPojo = createDocumentenDaoPojo(transactionInfo, transactionDocument, bedrag);

        DocumentProviderDocumentsDaoPojo documentProviderDocumentsDaoPojo = new DocumentProviderDocumentsDaoPojo();
        documentProviderDocumentsDaoPojo.setDocumentProvider(documentProviderDaoPojo);
        documentProviderDocumentsDaoPojo.setDocumenten(documentenDaoPojo);
        documentProviderDocumentsDaoPojo.setDateSent(transactionInfo.getTransactionDate());
        documentProviderDocumentsDaoPojo.setFeedback("Yuki ID: " + transactionInfo.getDocumentID() + " Received");

        documentProviderDocuments.save(documentProviderDocumentsDaoPojo);
    }
}
