/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.rest;

import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.controllers.model.DocumentenReport;
import com.xumpy.thuisadmin.controllers.model.NieuwDocument;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.thuisadmin.services.DocumentenSrv;
import com.xumpy.thuisadmin.services.model.DocumentenSrvPojo;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nico
 */
@Controller
@Scope("session")
public class CtrlDocumenten implements Serializable{
    @Autowired
    private DocumentenSrv documentenSrv;
    
    @RequestMapping("/json/fetch_documenten")
     public @ResponseBody List<DocumentenReport> fetchDocumenten(){
         return documentenSrv.fetchDocumentenReport();
     }
     
    @RequestMapping("/json/delete_document")
    public @ResponseBody String deleteDocument(@RequestBody NieuwDocument nieuwDocument){
        DocumentenSrvPojo document = new DocumentenSrvPojo();
        
        document.setPk_id(nieuwDocument.getPk_id());
        document.setOmschrijving(nieuwDocument.getOmschrijving());
        
        documentenSrv.delete(document);
        
        return "1";
    }
    
    @RequestMapping(value="/json/fetch_document/{documentId}", method = RequestMethod.GET)
    public @ResponseBody String fetchDocumentBlob(@PathVariable Integer documentId, HttpServletResponse response) throws IOException, SQLException{
        OutputStream out = response.getOutputStream();
        Documenten documenten = documentenSrv.fetchDocument(documentId);
        
        response.setContentType(documenten.getDocument_mime());
        response.setHeader("Content-Disposition", "inline;filename=\"" + documenten.getDocument_naam() + "\"");
       
        out.write(documenten.getDocument());
        
        out.flush();
        out.close();
        
        return null;
    }
 
    @RequestMapping("/json/fetch_bedrag_documenten/{bedragId}")
     public @ResponseBody List<NieuwDocument> fetchDocumentByBedrag(@PathVariable Integer bedragId){
         List<? extends Documenten> documenten = documentenSrv.fetchDocumentByBedrag(bedragId);
         
         List<NieuwDocument> nieuwDocumenten = new ArrayList<NieuwDocument>();
         
         for(Documenten document: documenten){
             NieuwDocument nieuwDocument = new NieuwDocument();
             nieuwDocument.setBedrag_id(document.getBedrag().getPk_id());
             //nieuwDocument.setDatum(document.getDatum());
             nieuwDocument.setOmschrijving(document.getOmschrijving());
             nieuwDocument.setPk_id(document.getPk_id());
             
             nieuwDocumenten.add(nieuwDocument);
         }
         
         return nieuwDocumenten;
     }
}
