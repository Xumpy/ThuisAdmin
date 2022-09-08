package com.xumpy.finances.controller;

import com.itextpdf.text.DocumentException;
import com.xumpy.documenprovider.dao.implementations.DocumentProviderDocumentsImpl;
import com.xumpy.documenprovider.dao.implementations.DocumentProviderImpl;
import com.xumpy.documenprovider.dao.implementations.DocumentProviderValidImpl;
import com.xumpy.documenprovider.dao.model.DocumentProviderDocumentsDaoPojo;
import com.xumpy.documenprovider.dao.model.DocumentProviderValidDaoPojo;
import com.xumpy.documenprovider.domain.DocumentProvider;
import com.xumpy.documenprovider.services.DocumentProviderSrv;
import com.xumpy.finances.excelbuilder.ExcelZipBuilder;
import com.xumpy.finances.services.AccountService;
import com.xumpy.finances.services.OverviewPDFBuilder;
import com.xumpy.thuisadmin.controllers.model.OverzichtGroepBedragen;
import com.xumpy.thuisadmin.dao.implementations.BedragAccountingDaoImpl;
import com.xumpy.thuisadmin.dao.model.DocumentenDaoPojo;
import com.xumpy.thuisadmin.dao.model.MonthlyValue;
import com.xumpy.thuisadmin.domain.Documenten;
import com.xumpy.thuisadmin.services.DocumentenSrv;
import com.xumpy.timesheets.dao.model.JobVatCompensationDaoPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class AccountController {
    @Autowired ExcelZipBuilder excelZipBuilder;
    @Autowired List<DocumentProviderSrv> documentProviders;
    @Autowired DocumentenSrv documentenSrv;
    @Autowired DocumentProviderValidImpl documentProviderValidImpl;
    @Autowired DocumentProviderDocumentsImpl documentProviderDocumentsImpl;
    @Autowired AccountService accountService;
    @Autowired OverviewPDFBuilder overviewPDFBuilder;

    private Integer year;

    public void setYear(Integer year){
        this.year = year;
    }

    public AccountController(){
        year = Calendar.getInstance().get(Calendar.YEAR);
    }

    @RequestMapping(value = "/accounting/accountingModel")
    public String viewAccountingModel(Model model){
        model.addAttribute("year", year);

        return "finances/accountingModel";
    }

    @RequestMapping(value = "/accounting/setyear/{year}")
    public String setYear(@PathVariable Integer year, Model model){
        this.year = year;

        model.addAttribute("year", year);

        return "finances/accountingCodes";
    }


    @RequestMapping(value = "/accounting/accountingCodes/{year}")
    public String viewAccountingCodes(@PathVariable Integer year, Model model){
        this.year = year;

        model.addAttribute("year", year);

        return "finances/accountingCodes";
    }

    private LocalDate lastDayOfMonth(LocalDate localDate){
        return localDate.withDayOfMonth(localDate.lengthOfMonth());
    }

    @RequestMapping(value="/json/generateAccountingZip", method = RequestMethod.GET)
    public @ResponseBody String generateAccounting(@RequestParam("startDate") String startDate,
                                                   @RequestParam("endDate") String endDate,
                                                   HttpServletResponse response) throws IOException{
        OutputStream out = response.getOutputStream();

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "inline;filename=\"accounting.zip\"");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        LocalDate localStartDate = LocalDate.parse("01/" + startDate, formatter);
        LocalDate localEndDate  = lastDayOfMonth(LocalDate.parse("01/" + endDate, formatter));

        out.write(excelZipBuilder.buildZip(localStartDate, localEndDate).toByteArray());

        out.flush();
        out.close();

        return null;
    }

    @RequestMapping(value="/json/sendToDocumentProviders", method = RequestMethod.GET)
    public String sendToDocumentProviders(@RequestParam("documentId") Integer documentId) throws IOException, JAXBException {
        Documenten document = documentenSrv.fetchDocument(documentId);

        for(DocumentProviderSrv documentProvider: documentProviders){
            for (DocumentProviderValidDaoPojo documentProviderValidDaoPojo: documentProviderValidImpl.findAllValidDocumentProviders(document.getBedrag().getDatum())){
                if (documentProviderValidDaoPojo.getDocumentProvider().getPkId().equals(documentProvider.getDocumentProviderId()) &&
                        !accountService.isDocumentSentToDocumentProvider(document, documentProviderValidDaoPojo.getDocumentProvider())){
                    String feedback = documentProvider.process(document);

                    DocumentProviderDocumentsDaoPojo documentProviderDocumentsDaoPojo = new DocumentProviderDocumentsDaoPojo();
                    documentProviderDocumentsDaoPojo.setDocumenten(new DocumentenDaoPojo(document));
                    documentProviderDocumentsDaoPojo.setDocumentProvider(documentProviderValidDaoPojo.getDocumentProvider());
                    documentProviderDocumentsDaoPojo.setDateSent(new Date());
                    documentProviderDocumentsDaoPojo.setFeedback(feedback);

                    documentProviderDocumentsImpl.save(documentProviderDocumentsDaoPojo);
                }
            }
        }

        return "redirect:/finances/nieuwBedrag/" + document.getBedrag().getPk_id();
    }

    @RequestMapping(value="/json/pdf_overview_bedragen", method = RequestMethod.POST)
    public @ResponseBody String fetchOverviewBedragen(@RequestBody List<OverzichtGroepBedragen> overzichtGroepBedragen) throws IOException, ParseException, DocumentException {
        return overviewPDFBuilder.buildPDFOveriew(overzichtGroepBedragen);
    }
}
