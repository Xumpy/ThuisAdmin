/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controller;

import com.xumpy.thuisadmin.model.db.Documenten;
import com.xumpy.thuisadmin.model.view.DocumentenReport;
import com.xumpy.thuisadmin.services.DocumentenSrv;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Nico
 */
@Controller
@Scope("session")
public class CtrlDocumenten {
    @Autowired
    private DocumentenSrv documentenSrv;
    
    @RequestMapping("/json/fetch_documenten")
     public @ResponseBody List<DocumentenReport> fetchDocumenten(){
         return documentenSrv.fetchDocumentenReport();
     }
    
    @RequestMapping(value="/json/fetch_document/{documentId}", method = RequestMethod.GET)
    public @ResponseBody String fetchDocumentBlob(@PathVariable Integer documentId, HttpServletResponse response){
        
        Documenten doc = documentenSrv.fetchDocument(documentId);
        
        response.setContentType("application/octet-stream");
        
        try {
            response.setHeader("Content-Disposition", "inline;filename=\"" +"Treinabonnement.pdf"+ "\"");
            OutputStream out = response.getOutputStream();
            IOUtils.copy(doc.getDocument().getBinaryStream(), out);
            out.flush();
            out.close();
         
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return null;
    }
    
}
