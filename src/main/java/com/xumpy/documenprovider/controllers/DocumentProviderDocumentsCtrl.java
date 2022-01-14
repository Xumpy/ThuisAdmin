package com.xumpy.documenprovider.controllers;

import com.xumpy.documenprovider.controllers.model.DocumentProviderDocumentsCtrlPojo;
import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.dao.model.DocumentProviderDocumentsDaoPojo;
import com.xumpy.documenprovider.domain.DocumentProviderDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DocumentProviderDocumentsCtrl {

    @Autowired DocumentProviderDocumentsImpl DocumentProviderDocumentsImpl;

    private List<DocumentProviderDocumentsCtrlPojo> createDocumentProviderDocumentsCtrl(List<? extends DocumentProviderDocuments> documentProviderDocuments){
        List<DocumentProviderDocumentsCtrlPojo> documentProviderDocumentsCtrlPojos = new ArrayList<>();

        for (DocumentProviderDocuments documentProviderDocument: documentProviderDocuments){
            DocumentProviderDocumentsCtrlPojo documentProviderDocumentsCtrlPojo = new DocumentProviderDocumentsCtrlPojo(documentProviderDocument);

            documentProviderDocumentsCtrlPojo.getDocumenten().setDocument(null);

            documentProviderDocumentsCtrlPojos.add(documentProviderDocumentsCtrlPojo);
        }

        return documentProviderDocumentsCtrlPojos;
    }

    @RequestMapping(value = "/documentprovider/documents")
    public  @ResponseBody List<DocumentProviderDocumentsCtrlPojo> getDocumentProviderDocuments(@RequestParam("documentId") Integer documentId){
        return createDocumentProviderDocumentsCtrl(DocumentProviderDocumentsImpl.getDocumentProviderDocumentsByDocumentId(documentId));
    }

    @RequestMapping(value = "/documentprovider/deleteDocumentProviderDocument")
    public String deleteDocumentProviderDocuments(@RequestParam("documentProviderDocumentId") Integer documentProviderDocumentId){
        DocumentProviderDocumentsDaoPojo documentProviderDocumentsDaoPojo =  DocumentProviderDocumentsImpl.findById(documentProviderDocumentId).get();
        Integer documentId = documentProviderDocumentsDaoPojo.getDocumenten().getPk_id();

        DocumentProviderDocumentsImpl.deleteById(documentProviderDocumentId);

        return "redirect:/finances/editBedragDocument/" + documentId;
    }
}
