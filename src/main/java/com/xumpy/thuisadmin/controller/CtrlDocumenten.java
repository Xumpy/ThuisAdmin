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
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Nico
 */
@Controller
@Scope("session")
public class CtrlDocumenten implements Serializable {
    @Autowired
    private DocumentenSrv documentenSrv;
    
    @RequestMapping("/json/fetch_documenten")
     public @ResponseBody List<DocumentenReport> fetchDocumenten(){
         return documentenSrv.fetchDocumentenReport();
     }
    
    @RequestMapping(value="/json/fetch_document/{documentId}", method = RequestMethod.GET)
    public @ResponseBody String fetchDocumentBlob(@PathVariable Integer documentId, HttpServletResponse response) throws IOException, SQLException{
        response.setContentType("application/octet-stream");
        
        OutputStream out = response.getOutputStream();
        Documenten documenten = documentenSrv.fetchDocument(documentId);
        
        response.setHeader("Content-Disposition", "inline;filename=\"" + documenten.getDocument_naam() + "\"");
       
        out.write(documenten.getDocument());
        
        out.flush();
        out.close();
        
        return null;
    }
    
}
