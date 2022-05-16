package com.xumpy.documenprovider.controllers;

import com.xumpy.documenprovider.controllers.model.DocumentProviderDocumentsCtrlPojo;
import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.dao.model.DocumentProviderDocumentsDaoPojo;
import com.xumpy.documenprovider.domain.DocumentProviderDocuments;
import com.xumpy.documenprovider.model.DPDocument;
import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.documenprovider.services.implementations.exceptions.PinNotValidException;
import com.xumpy.documenprovider.services.model.DocumentProviderDocumentsSrvPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class DocumentProviderDocumentsCtrl {

    @Autowired List<DocumentProviderSrv> documentProviderSrvs;
    @Autowired DocumentProviderDocumentsImpl documentProviderDocumentsImpl;

    private Map securityKeys;

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
        return createDocumentProviderDocumentsCtrl(documentProviderDocumentsImpl.getDocumentProviderDocumentsByDocumentId(documentId));
    }

    @RequestMapping(value = "/documentprovider/deleteDocumentProviderDocument")
    public String deleteDocumentProviderDocuments(@RequestParam("documentProviderDocumentId") Integer documentProviderDocumentId){
        DocumentProviderDocumentsDaoPojo documentProviderDocumentsDaoPojo =  documentProviderDocumentsImpl.findById(documentProviderDocumentId).get();
        Integer documentId = documentProviderDocumentsDaoPojo.getDocumenten().getPk_id();

        documentProviderDocumentsImpl.deleteById(documentProviderDocumentId);

        return "redirect:/finances/editBedragDocument/" + documentId;
    }


    @RequestMapping("/json/documentProvider/updateSecurityKeys")
    public @ResponseBody String updateCookie(@RequestBody Map securityKeys) {
        this.securityKeys = securityKeys;

        return "200";
    }

    @RequestMapping("/json/documentProvider/updateAccounting")
    public @ResponseBody String udpateAccounting(@RequestBody DocumentProviderDocumentsCtrlPojo documentProviderDocumentsCtrlPojo) throws PinNotValidException {
        for(DocumentProviderSrv documentProviderSrv: documentProviderSrvs){
            if (documentProviderSrv.getDocumentProviderId().equals(documentProviderDocumentsCtrlPojo.getDocumentProvider().getPkId())){
                documentProviderSrv.updateAccountingBedragen(new DocumentProviderDocumentsSrvPojo(documentProviderDocumentsCtrlPojo), securityKeys);
            }
        }
        return "200";
    }

    @RequestMapping("/json/documentProvider/updateFeedback/{documentProviderDocumentId}")
    public @ResponseBody List<DPDocument> updateFeedback(@PathVariable Integer documentProviderDocumentId) throws PinNotValidException {
        DocumentProviderDocumentsDaoPojo documentProviderDocumentsDaoPojo = documentProviderDocumentsImpl.findById(documentProviderDocumentId).get();

        for(DocumentProviderSrv documentProviderSrv: documentProviderSrvs){
            if (documentProviderSrv.getDocumentProviderId().equals(documentProviderDocumentsDaoPojo.getDocumentProvider().getPkId())){
                return documentProviderSrv.updateFeedback(documentProviderDocumentsDaoPojo.getDocumenten(), securityKeys);
            }
        }

        return new ArrayList<>();
    }

    @RequestMapping("/json/documentProvider/updateFeedback/{documentProviderDocumentId}/{guid}")
    public @ResponseBody List<DocumentProviderDocumentsCtrlPojo> updateFeedback(@PathVariable Integer documentProviderDocumentId, @PathVariable String guid) throws PinNotValidException {
        DocumentProviderDocumentsDaoPojo documentProviderDocumentsDaoPojo = documentProviderDocumentsImpl.findById(documentProviderDocumentId).get();
        documentProviderDocumentsDaoPojo.setFeedback("Exact ID: " + guid);

        documentProviderDocumentsImpl.save(documentProviderDocumentsDaoPojo);

        return createDocumentProviderDocumentsCtrl(documentProviderDocumentsImpl.getDocumentProviderDocumentsByDocumentId(documentProviderDocumentsDaoPojo.getDocumenten().getPk_id()));
    }
}
