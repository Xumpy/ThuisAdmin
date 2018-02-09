/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.thuisadmin.controllers.pages;

import com.xumpy.thuisadmin.controllers.model.NieuwDocument;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.thuisadmin.services.BedragenSrv;
import com.xumpy.thuisadmin.services.DocumentenSrv;
import com.xumpy.thuisadmin.services.model.BedragenSrvPojo;
import com.xumpy.thuisadmin.services.model.DocumentenSrvPojo;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Nico
 */
@Controller
public class Finances implements Serializable{
    @Autowired
    private BedragenSrv bedragenSrv;
    
    @Autowired
    private DocumentenSrv documentenSrv;
    
    private static final Logger Log = Logger.getLogger(Finances.class.getName());
    
    @RequestMapping(value = "finances/overview")
    public String viewOverview(){
        return "finances/overview";
    }
    
    @RequestMapping(value = "finances/beheerBedragen")
    public String viewBeheerBedragen(){
        return "finances/beheerBedragen";
    }
    
    @RequestMapping(value = "finances/documenten")
    public String viewDocumenten(){
        return "finances/documenten";
    }
    
    @RequestMapping(value = "finances/nieuwBedrag")
    public String viewNieuwBedrag(){
        return "finances/nieuwBedrag";
    }
    
    @RequestMapping(value = "finances/overzichtPerGroep")
    public String viewOverzichtPerGroep(){
        return "finances/overzichtPerGroep";
    }

    @RequestMapping(value = "finances/nieuwBedrag/{bedragId}")
    public String viewNieuwBedrag(@PathVariable Integer bedragId, Model model){
        model.addAttribute("pk_id", bedragId);
        
        return "finances/nieuwBedrag";
    }
    
     
    @RequestMapping(value = "finances/nieuwBedragDocument/{bedragId}")
    public String nieuwBedragDocument(@PathVariable Integer bedragId, Model model){
        model.addAttribute("bedragId", bedragId);
        model.addAttribute("document", new NieuwDocument());

        NieuwDocument nieuwDocument = new NieuwDocument();
        nieuwDocument.setBedragId(bedragId);
        nieuwDocument.setOmschrijving("");

        model.addAttribute("document", nieuwDocument);

        return "finances/nieuwDocument";
    }
     
    @RequestMapping(value = "finances/OverviewMonthCategory")
    public String OverviewMonthCategory(){
        return "finances/OverviewMonthCategory";
    }

    @RequestMapping(value = "finances/OverviewMonth")
    public String OverviewMonth(){
        return "finances/OverviewMonth";
    }

    @RequestMapping(value="/finances/editBedragDocument/{documentId}")
    public String editBedragDocument(@PathVariable Integer documentId, Model model){
        model.addAttribute("pk_id", documentId);
        
        Documenten document = documentenSrv.fetchDocument(documentId);
        
        model.addAttribute("bedragId", document.getBedrag().getPk_id());
        NieuwDocument nieuwDocument = new NieuwDocument();
        nieuwDocument.setBedragId(document.getBedrag().getPk_id());
        nieuwDocument.setOmschrijving(document.getOmschrijving());
        nieuwDocument.setPkId(document.getPk_id());
        
        model.addAttribute("document", nieuwDocument);
        
        return "finances/nieuwDocument";
    }
    
    
    @PostMapping("/finances/nieuwBedragDocument/saveDocument")
    public String saveBedragDocument(@ModelAttribute("document") NieuwDocument document, @RequestParam("file") MultipartFile file) throws IOException{
        System.out.println("document.getBedragId(): " + document.getBedragId());

        DocumentenSrvPojo bedragDocument = new DocumentenSrvPojo();

        bedragDocument.setBedrag(new BedragenSrvPojo(bedragenSrv.findBedrag(document.getBedragId())));
        //bedragDocument.setDatum(document.getDatum());
        bedragDocument.setDocument(file.getBytes());
        bedragDocument.setDocument_mime(file.getContentType());
        bedragDocument.setDocument_naam(file.getOriginalFilename());
        bedragDocument.setOmschrijving(document.getOmschrijving());
        bedragDocument.setPk_id(document.getPkId());
        
        Log.info("File bytes: " + file.getBytes().length);
        Log.info("Document PK_ID: " + document.getPkId());
        
        if (file.getBytes().length == 0 && document.getPkId() != null){
           Documenten bedragDocumentOld = documentenSrv.fetchDocument(document.getPkId());
           bedragDocument.setDocument(bedragDocumentOld.getDocument());
           bedragDocument.setDocument_mime(bedragDocumentOld.getDocument_mime());
           bedragDocument.setDocument_naam(bedragDocumentOld.getDocument_naam());
        }
        
        documentenSrv.save(bedragDocument);
        
        return "redirect:/finances/nieuwBedrag/" + document.getBedragId();
    }
}
